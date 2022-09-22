package com.benshapiro.composeexamples.ui.MainScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.focus.FocusDirection
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.benshapiro.composeexamples.InputValidator
import com.benshapiro.composeexamples.Screen
import com.benshapiro.composeexamples.base.EditableUserInputState
import com.benshapiro.composeexamples.model.InputWrapper
import com.benshapiro.composeexamples.model.Person
import com.benshapiro.composeexamples.navigation.ScreenEvent
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

const val FIRST_NAME = "first name"
const val LAST_NAME = "last name"
const val AGE = "age"

enum class FocusedTextFieldKey {
    FIRST_NAME, LAST_NAME, AGE, NONE
}

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val handle: SavedStateHandle
) : ViewModel() {
// enter field names in the viewModel rather than the UI layer
    val firstName = handle.getStateFlow(FIRST_NAME, InputWrapper(boxName = "First name"))
    val lastName = handle.getStateFlow(LAST_NAME, InputWrapper(boxName = "Last name"))
    val age = handle.getStateFlow(AGE, InputWrapper(boxName = "Age"))
    private val areInputsValid = combine(firstName, lastName, age) { firstName, lastName, age ->
        firstName.value.isNotEmpty() && firstName.errorId == null &&
        lastName.value.isNotEmpty() && lastName.errorId == null &&
        age.value.isNotEmpty() && age.errorId == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)
    // give some time before timeout
    // invoked for input into any input field, combines all input fields

    private val _events = Channel<ScreenEvent>()
    val events = _events.receiveAsFlow()
    // sealed class to handle all one time events

    fun onFirstNameEntered(input: String) {
        val errorId = InputValidator.getFirstNameErrorIdOrNull(input)
        handle[FIRST_NAME] = firstName.value.copy(value = input, errorId = errorId)
    }

    fun onLastNameEntered(input: String) {
        val errorId = InputValidator.getLastNameErrorIdOrNull(input)
        handle[LAST_NAME] = lastName.value.copy(value = input, errorId = errorId)
    }

    fun onAgeEntered(input: String) {
        val errorId = InputValidator.getAgeErrorIdOrNull(input)
        handle[AGE] = age.value.copy(value = input, errorId = errorId)
    }
    // recomposes composable listening for updates
    // triggers the valid flow and uses latest info

    private var focusedTextField = handle.get("focusedTextField") ?: FocusedTextFieldKey.FIRST_NAME
        set(value) {
            field = value
            handle.set("focusedTextField", value)
        }
    // store name of last focused field, first name is top so is default if nothing known

    init {
        if (focusedTextField != FocusedTextFieldKey.NONE) focusOnLastSelectedTextField()
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

    fun onAgeImeAction(){
        onContinueClick()
    }

    fun onContinueClick() {
        viewModelScope.launch(Dispatchers.Default) {
            if (areInputsValid.value) clearFocusAndHideKeyboard()
            val resId = if (areInputsValid.value) "SUCCESS" else "ERROR"
            _events.send(ScreenEvent.ShowToast(resId))
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

    val firstNameState: EditableUserInputState = EditableUserInputState(
        boxName = "First name",
        hint = "Enter first name",
        initialText = "Enter first name",
        false,
        ""
    )
    val lastNameState: EditableUserInputState = EditableUserInputState(
        boxName = "Last name",
        hint = "Enter last name",
        initialText = "Enter last name",
        false,
        ""
    )

    val ageState: EditableUserInputState = EditableUserInputState(
        boxName = "Age",
        hint = "Enter age",
        initialText = "Enter age",
        false,
        ""
    )

    private val db: FirebaseFirestore = Firebase.firestore

    fun saveAction(context: Context) {
        ageState.isSavePressed = true
        lastNameState.isSavePressed = true
        firstNameState.isSavePressed = true
        if (validateTextField(context)) {
            createPerson()
        } else {
            Toast.makeText(context, "Ensure fields are all entered", Toast.LENGTH_LONG).show()
        }
        validateNumberField(
            /*TODO(find my validation rules from prices)*/
        )
    }

    private fun createPerson() {
        val person = Person(
            "",
            firstNameState.text,
            lastNameState.text,
            ageState.text.toInt(),
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

    private fun validateTextField(context: Context): Boolean {
        return if (
            firstNameState.isHint || lastNameState.isHint || ageState.isHint ||
            firstNameState.text.isBlank() || lastNameState.text.isBlank() ||
            ageState.text.isBlank() || !ageState.text.isDigitsOnly()
        ) {
            Toast.makeText(context, "Ensure fields are all entered", Toast.LENGTH_LONG).show()
            false
        } else {
            Toast.makeText(context, "Person created", Toast.LENGTH_LONG).show()
            Log.d("Text fields", "valid")
            true
        }

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
}