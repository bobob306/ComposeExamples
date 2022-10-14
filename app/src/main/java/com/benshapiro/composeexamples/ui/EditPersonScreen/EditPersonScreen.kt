package com.benshapiro.composeexamples.ui.EditPersonScreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.benshapiro.composeexamples.base.ComposeExamplesButton
import com.benshapiro.composeexamples.base.ErrorHandlingUserInput

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditPersonScreen(
    navController: NavController,
    viewModel: EditPersonScreenViewModel = viewModel(),
    personId: String,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val firstNameFocusRequester = remember { FocusRequester() }
    val lastNameFocusRequester = remember { FocusRequester() }
    val ageFocusRequester = remember { FocusRequester() }

    val person = viewModel.getPersonById(personId)
    Surface(
        color = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            ErrorHandlingUserInput(
                modifier = Modifier
                    .focusRequester(firstNameFocusRequester)
                    .onFocusChanged { /*TODO()*/
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
                state = viewModel.firstNameInput,
                onValueChange = viewModel::onFNInputEntered,
                onImeKeyAction = { },
                keyboardOptions = remember {
                    KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Words
                    )
                },
            )
            ErrorHandlingUserInput(
                modifier = Modifier
                    .focusRequester(lastNameFocusRequester)
                    .onFocusChanged { /*TODO()*/
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
                state = viewModel.lastNameInput,
                onValueChange = viewModel::onLNInputEntered,
                onImeKeyAction = { },
                keyboardOptions = remember {
                    KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Words
                    )
                },
            )
            ErrorHandlingUserInput(
                modifier = Modifier
                    .focusRequester(ageFocusRequester)
                    .onFocusChanged { /*TODO()*/
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
                state = viewModel.ageInput,
                onValueChange = viewModel::onAgeInputEntered,
                onImeKeyAction = { },
                keyboardOptions = remember {
                    KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    )
                },
            )
            ComposeExamplesButton(
                buttonName = "Save",
                onClick = {
                    viewModel.onContinueClick(navController)
                    Log.d("Click", "Registered")
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

