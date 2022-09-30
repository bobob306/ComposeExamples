package com.benshapiro.composeexamples.ui.MainScreen

import android.util.Log
import androidx.compose.ui.focus.FocusDirection
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.benshapiro.composeexamples.extensions.InputValidator
import com.benshapiro.composeexamples.navigation.Screen
import com.benshapiro.composeexamples.base.ErrorHandlingInputState
import com.benshapiro.composeexamples.model.Person
import com.benshapiro.composeexamples.navigation.ScreenEvent
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

const val FIRST_NAME = "first name"
const val LAST_NAME = "last name"
const val AGE = "age"

enum class FocusedTextFieldKey {
    FIRST_NAME, LAST_NAME, AGE, NONE
}

@HiltViewModel
class MainScreenViewModel @Inject constructor
    (
    private val handle: SavedStateHandle
) : ViewModel() {
    // enter field names in the viewModel rather than the UI layer

//    val areInputsValid = combine(firstNameInput, lastNameInput, ageInput) { firstName, lastName, age ->
//        if (firstName.errorMessageLabel == null && lastName.errorMessageLabel == null && age.errorMessageLabel == null &&
//            firstName.text.isNotEmpty() && lastName.text.isNotEmpty() &&
//            age.text.isNotEmpty()
//        ) true else false
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(50000L), false)
    // give some time before timeout
    // invoked for input into any input field, combines all input fields
    // this doesnt work but a seemingly similar function validateFields() works great???

//    private val areInputsValidSimple: Boolean =
//        if (firstNameInput.value.errorMessageLabel == null && firstNameInput.value.text.isNotEmpty() &&
//            lastNameInput.value.errorMessageLabel == null && lastNameInput.value.text.isNotEmpty() &&
//            ageInput.value.errorMessageLabel == null && ageInput.value.text.isNotEmpty()
//        ) true else false

    private val _events = Channel<ScreenEvent>()
    val events = _events.receiveAsFlow()
    // sealed class to handle all one time events


    private var focusedTextField = handle.get("focusedTextField") ?: FocusedTextFieldKey.FIRST_NAME
        set(value) {
            field = value
            handle.set("focusedTextField", value)
        }
    // store name of last focused field, first name is top so is default if nothing known

    init {
        if (focusedTextField != FocusedTextFieldKey.FIRST_NAME) focusOnLastSelectedTextField()
    }
    // start by showing the last known/default field, and show the right kind of keyboard

    fun onTextFieldFocusChanged(key: FocusedTextFieldKey, isFocused: Boolean) {
        focusedTextField = if (isFocused) key else FocusedTextFieldKey.NONE
    }
    // gets called when composables have focus events and clears when unfocused events happen

    fun onFirstNameImeActionClick() {
        _events.trySend(ScreenEvent.MoveFocus(FocusDirection.Down))
    }
    // super cool, moves down to next field unless at bottom field, then does onClick

    fun onLastNameImeActionClick() {
        _events.trySend(ScreenEvent.MoveFocus(FocusDirection.Down))
    }

    fun onAgeImeAction() {
        onContinueClick()
    }

    fun onContinueClick() {
        viewModelScope.launch(Dispatchers.Default) {
            if (validateFields()) {
                createPerson()
            }
            val resId = if (validateFields()) "SUCCESS" else "ERROR"
            _events.send(ScreenEvent.ShowToast(resId))
            if (validateFields()) {
                delay(500L)
                clearFocusAndHideKeyboard()
                onClearClicked()
            }
        }
    }

    // unfocus text field, hide keyboard

    private suspend fun clearFocusAndHideKeyboard() {
        _events.send(ScreenEvent.ClearFocus)
        _events.send(ScreenEvent.UpdateKeyboard(false))
        focusedTextField = FocusedTextFieldKey.NONE
    }

    private fun focusOnLastSelectedTextField() {
        viewModelScope.launch(Dispatchers.Default) {
            _events.send(ScreenEvent.RequestFocus(focusedTextField))
            delay(250)
            _events.send(ScreenEvent.UpdateKeyboard(true))
        }
    }

    private val db: FirebaseFirestore = Firebase.firestore

    private fun createPerson() {
        val person = Person(
            "",
            firstNameInput.value.text,
            lastNameInput.value.text,
            ageInput.value.text?.toInt(),
        )
        db.collection("people")
            .add(person)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    "Success happy face",
                    "DocumentSnapshot added with ID: ${documentReference.id}"
                )
            }.addOnFailureListener { e ->
                Log.w("Failure sad face", "Error adding document", e)
            }
    }

    private fun validateFields() : Boolean {
        return if (
            firstNameInput.value.text!!.isBlank() || lastNameInput.value.text!!.isBlank()
            || ageInput.value.text!!.isBlank() || firstNameInput.value.errorMessageLabel != null
            || lastNameInput.value.errorMessageLabel != null || ageInput.value.errorMessageLabel !=  null
        ) false else true
    }

    private fun validateNumberField() {
        Log.d("Number fields", "valid")
    }

    fun toAnotherScreen(navController: NavController) {
        navController.navigate(Screen.AnotherScreen.route)
    }

    fun toViewPeopleScreen(navController: NavController) {
        navController.navigate(Screen.ViewUserScreen.route)
    }

    val firstNameInput = handle.getStateFlow(FIRST_NAME, ErrorHandlingInputState("First name", "Enter first name"))
    fun onFNInputEntered(input: String) {
        val error = InputValidator.getFirstNameErrorIdOrNull(input)
//        val errorId = firstNameInput.value.text?.let { InputValidator.getFirstNameErrorIdOrNull(it) }
        firstNameInput.value.errorMessageLabel = error
    }

    fun onClearClicked() {
        firstNameInput.value.text = ""
        lastNameInput.value.text = ""
        ageInput.value.text = ""
    }

    val lastNameInput = handle.getStateFlow(LAST_NAME, ErrorHandlingInputState("Last name", "Enter last name"))
    fun onLNInputEntered(input: String) {
        val errorId = InputValidator.getLastNameErrorIdOrNull(input)
        lastNameInput.value.errorMessageLabel = errorId
    }

    val ageInput = handle.getStateFlow(AGE, ErrorHandlingInputState("Age", "Enter age"))
    fun onAgeInputEntered(input: String) {
        val errorId = InputValidator.getAgeErrorIdOrNull(input)
        ageInput.value.errorMessageLabel = errorId
    }

}