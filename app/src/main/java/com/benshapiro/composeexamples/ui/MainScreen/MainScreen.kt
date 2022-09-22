package com.benshapiro.composeexamples.ui.MainScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.benshapiro.composeexamples.base.*
import com.benshapiro.composeexamples.navigation.ScreenEvent
import com.benshapiro.composeexamples.toast

@OptIn(ExperimentalComposeUiApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel = viewModel(),
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner){
        viewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }
    val firstName by viewModel.firstName.collectAsStateWithLifecycle()
    val lastName by viewModel.lastName.collectAsStateWithLifecycle()
    val age by viewModel.age.collectAsStateWithLifecycle()

    val firstNameFocusRequester = remember{FocusRequester()}
    val lastNameFocusRequester = remember{FocusRequester()}
    val ageFocusRequester = remember{FocusRequester()}

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is ScreenEvent.ShowToast -> context.toast(event.messageId)
                is ScreenEvent.UpdateKeyboard -> {
                    if (event.show) keyboardController?.show() else keyboardController?.hide()
                }
                is ScreenEvent.ClearFocus -> focusManager.clearFocus()
                is ScreenEvent.RequestFocus -> {
                    when (event.textFieldKey) {
                        FocusedTextFieldKey.FIRST_NAME -> firstNameFocusRequester.requestFocus()
                        FocusedTextFieldKey.LAST_NAME -> lastNameFocusRequester.requestFocus()
                        FocusedTextFieldKey.AGE -> ageFocusRequester.requestFocus()
                        else -> {}
                    }
                }
                is ScreenEvent.MoveFocus -> focusManager.moveFocus(event.direction)
            }
        }
    }

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
            CustomTextField(
                modifier = Modifier
                    .focusRequester(firstNameFocusRequester)
                    .onFocusChanged { focusState ->
                        viewModel.onTextFieldFocusChanged(
                            key = FocusedTextFieldKey.FIRST_NAME,
                            isFocused = focusState.isFocused
                        )
                    },
                labelResId = firstName.boxName ?: "",
                keyboardOptions = remember {
                    KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                },
                inputWrapper = firstName,
                onValueChange = viewModel::onFirstNameEntered,
                onImeKeyAction = viewModel::onFirstNameImeActionClick
            )
            CustomTextField(
                modifier = Modifier
                    .focusRequester(lastNameFocusRequester)
                    .onFocusChanged { focusState ->
                        viewModel.onTextFieldFocusChanged(
                            key = FocusedTextFieldKey.LAST_NAME,
                            isFocused = focusState.isFocused
                        )
                    },
                labelResId = lastName.boxName ?: "",
                keyboardOptions = remember {
                    KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                },
                inputWrapper = lastName,
                onValueChange = viewModel::onLastNameEntered,
                onImeKeyAction = viewModel::onLastNameImeActionClick
            )
            CustomTextField(
                modifier = Modifier
                    .focusRequester(ageFocusRequester)
                    .onFocusChanged { focusState ->
                        viewModel.onTextFieldFocusChanged(
                            key = FocusedTextFieldKey.AGE,
                            isFocused = focusState.isFocused
                        )
                    },
                onImeKeyAction = viewModel::onAgeImeAction,
                inputWrapper = age,
                labelResId = age.boxName ?: "",
                keyboardOptions = remember {
                    KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                },
                onValueChange = viewModel::onAgeEntered
            )
            Button(onClick = viewModel::onContinueClick) {
                Text(text = "Continue")
            }
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
        rememberEditableUserInputState(boxName = "First name", hint = "Enter first name", false,"")
    val editableUserInputStateLastName =
        rememberEditableUserInputState(boxName = "Last name", hint = "Enter last name", false, "")
    val editableUserInputStateAge =
        rememberEditableUserInputState(boxName = "Age", hint = "Enter your age", false, "")
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
