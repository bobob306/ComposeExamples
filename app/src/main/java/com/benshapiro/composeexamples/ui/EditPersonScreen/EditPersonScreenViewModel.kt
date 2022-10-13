package com.benshapiro.composeexamples.ui.EditPersonScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusDirection
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.benshapiro.composeexamples.base.ErrorHandlingInputState
import com.benshapiro.composeexamples.data.DataOrException
import com.benshapiro.composeexamples.extensions.InputValidator
import com.benshapiro.composeexamples.model.Person
import com.benshapiro.composeexamples.module.AppModule
import com.benshapiro.composeexamples.navigation.Screen
import com.benshapiro.composeexamples.navigation.ScreenEvent
import com.benshapiro.composeexamples.repository.PersonsRepository
import com.benshapiro.composeexamples.ui.MainScreen.AGE
import com.benshapiro.composeexamples.ui.MainScreen.FIRST_NAME
import com.benshapiro.composeexamples.ui.MainScreen.LAST_NAME
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPersonScreenViewModel @Inject constructor(
    private val repository: PersonsRepository,
    private val handle: SavedStateHandle,
) : ViewModel() {

    var loading = mutableStateOf(false)

    private var _editPerson: MutableState<DataOrException<Person, Exception>> =
        mutableStateOf(
            DataOrException(
                Person(),
                Exception("")
            )
        )
    val editPerson get() = _editPerson

    fun getPersonById(personId : String) {
        viewModelScope.launch {
            loading.value = true
            repository.getPersonFromFirestore(personId)
            _editPerson.value = repository.getPersonFromFirestore(personId)
            loading.value = false
            loadPerson()
        }
    }

    val firstNameInput = handle.getStateFlow(FIRST_NAME, ErrorHandlingInputState("First name"))
    fun onFNInputEntered(input: String) {
        val error = InputValidator.getFirstNameErrorIdOrNull(input)
        firstNameInput.value.errorMessageLabel = error
    }

    val lastNameInput = handle.getStateFlow(LAST_NAME, ErrorHandlingInputState("Last name"))
    fun onLNInputEntered(input: String) {
        val errorId = InputValidator.getLastNameErrorIdOrNull(input)
        lastNameInput.value.errorMessageLabel = errorId
    }

    val ageInput = handle.getStateFlow(AGE, ErrorHandlingInputState("Age"))
    fun onAgeInputEntered(input: String) {
        val errorId = InputValidator.getAgeErrorIdOrNull(input)
        ageInput.value.errorMessageLabel = errorId
    }

    fun toViewPeopleScreen(navController: NavController) {
        navController.navigate(Screen.ViewUserScreen.route)
    }

    fun onContinueClick() {
        viewModelScope.launch(Dispatchers.Default) {
            if (validateFields()) {
                editPersonEntry()
            }
            if (validateFields()) {
                delay(500L)
                onClearClicked()
            }
        }
    }

    private fun validateFields() : Boolean {
        return if (
            firstNameInput.value.text!!.isBlank() || lastNameInput.value.text!!.isBlank()
            || ageInput.value.text!!.isBlank() || firstNameInput.value.errorMessageLabel != null
            || lastNameInput.value.errorMessageLabel != null || ageInput.value.errorMessageLabel !=  null
        ) false else true
    }

    private val db: FirebaseFirestore = Firebase.firestore

    private fun loadPerson() {
        firstNameInput.value.text = editPerson.value.data!!.firstName
        lastNameInput.value.text = editPerson.value.data!!.lastName
        ageInput.value.text = editPerson.value.data!!.age.toString()
    }

    private fun editPersonEntry() {
        val person = Person(
            editPerson.value.data!!.id,
            firstNameInput.value.text,
            lastNameInput.value.text,
            ageInput.value.text?.toInt(),
        )
        db.collection("people")
            .document(person.id)
            .update(
                "age", person.age,
                "firstName", person.firstName,
                "lastName", person.lastName
            )
    }

    fun onClearClicked() {
        firstNameInput.value.text = ""
        lastNameInput.value.text = ""
        ageInput.value.text = ""
    }

}