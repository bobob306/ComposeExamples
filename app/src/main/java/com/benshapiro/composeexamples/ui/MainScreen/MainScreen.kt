package com.benshapiro.composeexamples.ui.MainScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.benshapiro.composeexamples.Screen
import com.benshapiro.composeexamples.base.*

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel = viewModel(),
) {
    Surface(
        color = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            val editableUserInputStateFirstName =
                rememberEditableUserInputState(boxName = "First name", hint = "Enter first name")
            val editableUserInputStateLastName =
                rememberEditableUserInputState(boxName = "Last name", hint = "Enter last name")
            val editableUserInputStateAge =
                rememberEditableUserInputState(boxName = "Age", hint = "Enter your age")
            namesForm(
                editableUserInputStateFirstName,
                editableUserInputStateLastName,
                editableUserInputStateAge
            )
            ComposeExamplesButton(
                buttonName = "Save",
                onClick = {
                    viewModel.saveAction(
                        editableUserInputStateFirstName.text,
                        editableUserInputStateLastName.text,
                        editableUserInputStateAge.text,
                    )
                    Log.d("Click", "Registered")
                },
            )
            ComposeExamplesButton(
                buttonName = "To another screen",
                onClick = {
                    navController.navigate(Screen.AnotherScreen.route)
                    Log.d("Another screen button", "Clicked")
                },
            )
        }
    }
}

@Composable
fun namesForm(
    editableUserInputStateFirstName: EditableUserInputState,
    editableUserInputStateLastName: EditableUserInputState,
    editableUserInputStateAge: EditableUserInputState
) {
    ComposeExamplesEditableTextUserInput(state = editableUserInputStateFirstName)
    ComposeExamplesEditableTextUserInput(state = editableUserInputStateLastName)
    ComposeExamplesEditableNumberUserInput(state = editableUserInputStateAge)
}


@Preview
@Composable
fun MainScreenPreview() {
    val editableUserInputStateFirstName =
        rememberEditableUserInputState(boxName = "First name", hint = "Enter first name")
    val editableUserInputStateLastName =
        rememberEditableUserInputState(boxName = "Last name", hint = "Enter last name")
    val editableUserInputStateAge =
        rememberEditableUserInputState(boxName = "Age", hint = "Enter your age")
    namesForm(
        editableUserInputStateFirstName,
        editableUserInputStateLastName,
        editableUserInputStateAge,
    )
}