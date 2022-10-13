package com.benshapiro.composeexamples.ui.ViewUserScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.benshapiro.composeexamples.data.DataOrException
import com.benshapiro.composeexamples.model.Person
import com.benshapiro.composeexamples.module.AppModule
import com.benshapiro.composeexamples.navigation.Screen
import com.benshapiro.composeexamples.repository.PersonsRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewUserScreenViewModel @Inject constructor(
    private val repository: PersonsRepository,
    private val handle: SavedStateHandle,
) : ViewModel() {

    val db = Firebase.firestore.collection("people")

    val dataOrException = AppModule.provideDataOrException()

    var loading = mutableStateOf(false)

    private var _personList: MutableState<DataOrException<List<Person>, Exception>> =
        mutableStateOf(
            DataOrException(
                listOf(),
                Exception("")
            )
        )
    val personList get() = _personList

    init {
        getPersons()
    }

    private fun getPersons() {
        viewModelScope.launch {
            loading.value = true
            _personList.value = repository.getPersonsFromFirestore()
            loading.value = false
        }
    }

    fun deletePerson(person: Person) {
        viewModelScope.launch {
            repository.deletePerson(person)
            getPersons()
        }
    }

    fun onEditClick(person: Person, navController: NavController) {
        navController.navigate(Screen.EditPersonScreen.withArgs(person.id))
    }

    fun returnHome(navController: NavController) {
        navController.navigate(Screen.MainScreen.route)
    }

}