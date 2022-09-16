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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.benshapiro.composeexamples.base.*

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel = viewModel(),
) {
    val context = LocalContext.current
    Surface(
        color = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            NamesForm(
                viewModel.firstNameState,
                viewModel.lastNameState,
                viewModel.ageState
            )
            ComposeExamplesButton(
                buttonName = "Save",
                onClick = {
                    viewModel.saveAction(context)
                    Log.d("Click", "Registered")
                },
            )
            ComposeExamplesButton(
                buttonName = "To another screen",
                onClick = {
                    viewModel.toAnotherScreen(navController)
                    Log.d("Another screen button", "Clicked")
                },
            )
            ComposeExamplesButton(
                buttonName = "To view people screen",
                onClick = {
                    viewModel.toViewPeopleScreen(navController)
                    Log.d("View people screen btn", "Clicked")
                },
            )
        }
    }
}

@Composable
fun NamesForm(
    firstNameState: EditableUserInputState,
    lastNameState: EditableUserInputState,
    ageState: EditableUserInputState
) {
    ComposeExamplesEditableTextUserInput(state = firstNameState)
    ComposeExamplesEditableTextUserInput(state = lastNameState)
    ComposeExamplesEditableNumberUserInput(state = ageState)
}


@Preview
@Composable
fun MainScreenPreview() {
    val editableUserInputStateFirstName =
        rememberEditableUserInputState(boxName = "First name", hint = "Enter first name", false)
    val editableUserInputStateLastName =
        rememberEditableUserInputState(boxName = "Last name", hint = "Enter last name", false)
    val editableUserInputStateAge =
        rememberEditableUserInputState(boxName = "Age", hint = "Enter your age", false)
    Surface(
        color = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        )
        {
            NamesForm(
                editableUserInputStateFirstName,
                editableUserInputStateLastName,
                editableUserInputStateAge,
            )
        }
    }
}
