package com.benshapiro.composeexamples.ui.MainScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.benshapiro.composeexamples.base.*
import com.benshapiro.composeexamples.navigation.ScreenEvent
import com.benshapiro.composeexamples.toast

@SuppressLint("StateFlowValueCalledInComposition")
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

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    val firstName by viewModel.firstNameInput.collectAsStateWithLifecycle()
    val lastName by viewModel.lastNameInput.collectAsStateWithLifecycle()
    val age by viewModel.ageInput.collectAsStateWithLifecycle()
//    val areInputsValid by viewModel.areInputsValid.collectAsStateWithLifecycle()

    val firstNameFocusRequester = remember { FocusRequester() }
    val lastNameFocusRequester = remember { FocusRequester() }
    val ageFocusRequester = remember { FocusRequester() }

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
//                        FocusedTextFieldKey.LAST_NAME -> lastNameFocusRequester.requestFocus()
//                        FocusedTextFieldKey.AGE -> ageFocusRequester.requestFocus()
                        else -> {}
                    }
                }
                is ScreenEvent.MoveFocus -> focusManager.moveFocus(event.direction)
                is ScreenEvent.ClearInputs -> viewModel.onClearClicked()
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
//            NamesForm(
//                viewModel.firstNameState,
//                viewModel.lastNameState,
//                viewModel.ageState
//            )
//            rememberErrorHandlingInputState(
//                label = viewModel.firstNameInput.value.labelText,
//                hint = viewModel.firstNameInput.value.text,
//                errorMessage = null
//            )
            ErrorHandlingUserInput(
                modifier = Modifier
                    .focusRequester(firstNameFocusRequester)
                    .onFocusChanged { focusState ->
                        viewModel.onTextFieldFocusChanged(
                            key = FocusedTextFieldKey.FIRST_NAME,
                            isFocused = focusState.isFocused
                        )
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
                state = viewModel.firstNameInput,
                onValueChange = viewModel::onFNInputEntered,
                onImeKeyAction = { viewModel.onFirstNameImeActionClick() },
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
                    .onFocusChanged { focusState ->
                        viewModel.onTextFieldFocusChanged(
                            key = FocusedTextFieldKey.LAST_NAME,
                            isFocused = focusState.isFocused
                        )
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
                state = viewModel.lastNameInput,
                onValueChange = viewModel::onLNInputEntered,
                onImeKeyAction = { viewModel.onLastNameImeActionClick() },
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
                    .focusRequester(firstNameFocusRequester)
                    .onFocusChanged { focusState ->
                        viewModel.onTextFieldFocusChanged(
                            key = FocusedTextFieldKey.AGE,
                            isFocused = focusState.isFocused
                        )
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
                state = viewModel.ageInput,
                onValueChange = viewModel::onAgeInputEntered,
                onImeKeyAction = { viewModel.onAgeImeAction() },
                keyboardOptions = remember {
                    KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    )
                },
            )
            ComposeExamplesButton(
                buttonName = "Clear",
                onClick = { viewModel.onClearClicked() }
            )
//            ComposeExamplesTextField(
//                modifier = Modifier
//                    .focusRequester(firstNameFocusRequester)
//                    .onFocusChanged { focusState ->
//                        viewModel.onTextFieldFocusChanged(
//                            key = FocusedTextFieldKey.FIRST_NAME,
//                            isFocused = focusState.isFocused
//                        )
//                    }
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//                    .padding(bottom = 8.dp),
//                labelResId = firstName.boxName ?: "",
//                keyboardOptions = remember {
//                    KeyboardOptions(
//                        keyboardType = KeyboardType.Text,
//                        imeAction = ImeAction.Next
//                    )
//                },
//                inputWrapper = firstName,
//                onValueChange = viewModel::onFirstNameEntered,
//                onImeKeyAction = viewModel::onFirstNameImeActionClick,
//                onSaveClicked = viewModel::onFirstNameCleared
//            )
//            ComposeExamplesTextField(
//                modifier = Modifier
//                    .focusRequester(lastNameFocusRequester)
//                    .onFocusChanged { focusState ->
//                        viewModel.onTextFieldFocusChanged(
//                            key = FocusedTextFieldKey.LAST_NAME,
//                            isFocused = focusState.isFocused
//                        )
//                    }
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//                    .padding(bottom = 8.dp),
//                labelResId = lastName.boxName ?: "",
//                keyboardOptions = remember {
//                    KeyboardOptions(
//                        keyboardType = KeyboardType.Text,
//                        imeAction = ImeAction.Next
//                    )
//                },
//                inputWrapper = lastName,
//                onValueChange = viewModel::onLastNameEntered,
//                onImeKeyAction = viewModel::onLastNameImeActionClick,
//                onSaveClicked = viewModel::onLastNameCleared
//            )
//            ComposeExamplesTextField(
//                modifier = Modifier
//                    .focusRequester(ageFocusRequester)
//                    .onFocusChanged { focusState ->
//                        viewModel.onTextFieldFocusChanged(
//                            key = FocusedTextFieldKey.AGE,
//                            isFocused = focusState.isFocused
//                        )
//                    }
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//                    .padding(bottom = 8.dp),
//                onImeKeyAction = viewModel::onAgeImeAction,
//                inputWrapper = age,
//                labelResId = age.boxName ?: "",
//                keyboardOptions = remember {
//                    KeyboardOptions(
//                        keyboardType = KeyboardType.Number,
//                        imeAction = ImeAction.Next
//                    )
//                },
//                onValueChange = viewModel::onAgeEntered,
//                onSaveClicked = viewModel::onAgeCleared
//            )
            ComposeExamplesButton(
                buttonName = "Save",
                onClick = {
                    viewModel.onContinueClick()
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

//@Composable
//fun NamesForm(
//    firstNameState: EditableUserInputState,
//    lastNameState: EditableUserInputState,
//    ageState: EditableUserInputState
//) {
//    ComposeExamplesEditableTextUserInput(state = firstNameState)
//    ComposeExamplesEditableTextUserInput(state = lastNameState)
//    ComposeExamplesEditableNumberUserInput(state = ageState)
//}


@Preview
@Composable
fun MainScreenPreview() {
    val editableUserInputStateFirstName =
        rememberEditableUserInputState(boxName = "First name", hint = "Enter first name", false, "")
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
//            NamesForm(
//                editableUserInputStateFirstName,
//                editableUserInputStateLastName,
//                editableUserInputStateAge,
//            )
        }
    }
}
