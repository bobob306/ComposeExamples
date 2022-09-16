package com.benshapiro.composeexamples.ui.MainScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.benshapiro.composeexamples.Screen
import com.benshapiro.composeexamples.base.EditableUserInputState
import com.benshapiro.composeexamples.model.Person
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
) : ViewModel() {

    val firstNameState: EditableUserInputState = EditableUserInputState(
        boxName = "First name",
        hint = "Enter first name",
        initialText = "Enter first name"
    )
    val lastNameState: EditableUserInputState = EditableUserInputState(
        boxName = "Last name",
        hint = "Enter last name",
        initialText = "Enter last name"
    )
    val ageState: EditableUserInputState = EditableUserInputState(
        boxName = "Age",
        hint = "Enter age",
        initialText = "Enter age"
    )

    private val db: FirebaseFirestore = Firebase.firestore

    fun saveAction(context: Context) {
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