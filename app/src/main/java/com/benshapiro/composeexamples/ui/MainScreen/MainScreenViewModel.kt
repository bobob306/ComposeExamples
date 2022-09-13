package com.benshapiro.composeexamples.ui.MainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
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
        val person = hashMapOf(
            "first" to firstName,
            "last" to lastName,
            "age" to age.toInt(),
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
}