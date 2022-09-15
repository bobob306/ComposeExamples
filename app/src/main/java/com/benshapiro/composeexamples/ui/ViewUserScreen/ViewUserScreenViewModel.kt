package com.benshapiro.composeexamples.ui.ViewUserScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benshapiro.composeexamples.data.DataOrException
import com.benshapiro.composeexamples.model.Person
import com.benshapiro.composeexamples.module.AppModule
import com.benshapiro.composeexamples.repository.PersonsRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewUserScreenViewModel @Inject constructor(
    private val repository: PersonsRepository,
    dataOrException: DataOrException<List<Person>, Exception>
) : ViewModel() {

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