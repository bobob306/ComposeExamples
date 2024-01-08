package com.benshapiro.composeexamples.ui.EditPersonScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.benshapiro.composeexamples.base.ErrorHandlingInputState
import com.benshapiro.composeexamples.data.DataOrException
import com.benshapiro.composeexamples.extensions.InputValidator
import com.benshapiro.composeexamples.model.Person
import com.benshapiro.composeexamples.navigation.Screen
import com.benshapiro.composeexamples.repository.PersonsRepository
import com.benshapiro.composeexamples.ui.MainScreen.AGE
import com.benshapiro.composeexamples.ui.MainScreen.*
import com.benshapiro.composeexamples.ui.MainScreen.LAST_NAME
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    fun getPersonById(personId: String) {
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

    val phoneNumberInput = handle.getStateFlow(PHONE_NUMBER, ErrorHandlingInputState("Phone Number"))
    fun onPhoneNumberInputEntered(input: String) {
        val errorId = InputValidator.getPhoneNumberErrorIdOrNull(input)
        phoneNumberInput.value.errorMessageLabel = errorId
    }

    fun toViewPeopleScreen(navController: NavController) {
        navController.navigate(Screen.ViewUserScreen.route)
    }

    fun onContinueClick(navController: NavController) {
        viewModelScope.launch(Dispatchers.Main) {
            if (validateFields()) {
                editPersonEntry()
                onClearClicked()
                toViewPeopleScreen(navController)
            }
        }
    }

    private fun validateFields(): Boolean {
        return !(firstNameInput.value.text!!.isBlank() || lastNameInput.value.text!!.isBlank()
                || ageInput.value.text!!.isBlank() || firstNameInput.value.errorMessageLabel != null
                || lastNameInput.value.errorMessageLabel != null || ageInput.value.errorMessageLabel != null
                || phoneNumberInput.value.text!!.isBlank() || phoneNumberInput.value.errorMessageLabel != null)
    }

    private val db: FirebaseFirestore = Firebase.firestore

    private fun loadPerson() {
        firstNameInput.value.text = editPerson.value.data!!.firstName
        lastNameInput.value.text = editPerson.value.data!!.lastName
        ageInput.value.text = editPerson.value.data!!.age.toString()
        phoneNumberInput.value.text = editPerson.value.data?.phoneNumber ?: ""
    }

    private fun editPersonEntry() {
        val person = Person(
            editPerson.value.data!!.id,
            firstNameInput.value.text,
            lastNameInput.value.text,
            ageInput.value.text?.toInt(),
            phoneNumberInput.value.text,
        )
        updatePerson(person)
    }

    private fun updatePerson(person: Person) {
        db.collection("people").document(person.id).update(
            "age", person.age,
            "firstName", person.firstName,
            "lastName", person.lastName,
            "phoneNumber", person.phoneNumber,
        )
    }

    fun onClearClicked() {
        firstNameInput.value.text = ""
        lastNameInput.value.text = ""
        ageInput.value.text = ""
        phoneNumberInput.value.text = ""
    }

}