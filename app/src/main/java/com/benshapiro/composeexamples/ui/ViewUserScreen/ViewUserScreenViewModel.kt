package com.benshapiro.composeexamples.ui.ViewUserScreen

import android.util.Log
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

    private var _personToEdit: MutableState<DataOrException<Person, Exception>> =
        mutableStateOf(
            DataOrException(
                Person(),
                Exception("")
            )
        )
    val personToEdit get() = handle.getStateFlow("personToEdit", _personToEdit)

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
        addPersonToHandle(person)
        navController.navigate(Screen.EditPersonScreen.withArgs(person.id))
    }

    private fun addPersonToHandle(person: Person) {
        viewModelScope.launch {
            val docRef = db.document(person.id)
            docRef.get().addOnSuccessListener {
                Log.d("another way", it.data.toString())
            }
            val test = repository.getPersonFromFirestore(person.id).data?.id ?: "ugh"
            _personToEdit = mutableStateOf(repository.getPersonFromFirestore(person.id))
            Log.d("mutable handle", _personToEdit.value.data.toString())
            Log.d("id", person.id)
            Log.d("test", test)
        }
        val personId =
            handle.set("personId", person.id)
        Log.d("person", handle.get<String>("personId").toString())
        Log.d("person", personId.toString())
    }

    fun returnHome(navController: NavController) {
        navController.navigate(Screen.MainScreen.route)
    }

//    private fun loadPersonDatabase() {
//        db.collection("people")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d("User", "${document.id} => ${document.data}")
//                    val listOfPerson = document.data.map {
//                        Person(
//                            firstName = document.data["firstName"].toString(),
//                            lastName = document.data["lastName"].toString(),
//                            age = document.data["age"].hashCode().toInt()
//                        )
//                    }
//                    Log.d("last person is", listOfPerson.last().firstName)
//                    _personList.value = listOfPerson
//                    Log.d("person ${result.documents.size} is", personList.value.last().firstName)
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w("Oops", "Error getting documents", exception)
//            }
//    }

}