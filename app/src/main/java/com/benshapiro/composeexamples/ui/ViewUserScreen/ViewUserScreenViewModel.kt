package com.benshapiro.composeexamples.ui.ViewUserScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.benshapiro.composeexamples.model.Person
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ViewUserScreenViewModel @Inject constructor(
) : ViewModel() {

    private val db = Firebase.firestore
    private var _personList = MutableStateFlow<List<Person>>(emptyList())
    val personList get() = _personList

    init {
        _personList.value = emptyList()
        loadPersonDatabase()
    }

    private fun loadPersonDatabase() {
        db.collection("people")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("User", "${document.id} => ${document.data}")
                    val listOfPerson  = document.data.map {
                        Person(
                            firstName = document.data["firstName"].toString(),
                            lastName = document.data["lastName"].toString(),
                            age = document.data["age"].hashCode().toInt()
                        )
                    }
                    Log.d("last person is", listOfPerson.last().firstName)
                    _personList.value = listOfPerson
                    Log.d("person ${result.documents.size} is", personList.value.last().firstName)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Oops", "Error getting documents", exception)
            }
    }

}