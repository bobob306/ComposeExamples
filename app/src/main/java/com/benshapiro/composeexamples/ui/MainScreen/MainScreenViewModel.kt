package com.benshapiro.composeexamples.ui.MainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.benshapiro.composeexamples.Screen
import com.benshapiro.composeexamples.model.Person
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
) : ViewModel() {

    private val db: FirebaseFirestore = Firebase.firestore

    fun saveAction(firstName: String, lastName: String, age: String) {
        validateTextField(
            /*TODO(find my validation rules from prices)*/
        )
        validateNumberField(
            /*TODO(find my validation rules from prices)*/
        )
        val person = Person(
            "",
            firstName,
            lastName,
            age.toInt(),
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

    private fun validateTextField() {
        Log.d("Text fields", "valid")
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