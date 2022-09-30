package com.benshapiro.composeexamples.ui.EditPersonScreen

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun EditPersonScreen (
    navController: NavController,
    viewModel: EditPersonScreenViewModel = viewModel(),
    personId: String,
) {
    Surface() {

    }
}

//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.FocusRequester
//import androidx.compose.ui.focus.focusRequester
//import androidx.compose.ui.focus.onFocusChanged
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalFocusManager
//import androidx.compose.ui.platform.LocalLifecycleOwner
//import androidx.compose.ui.platform.LocalSoftwareKeyboardController
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardCapitalization
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.flowWithLifecycle
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import com.benshapiro.composeexamples.base.ComposeExamplesButton
//import com.benshapiro.composeexamples.base.ErrorHandlingUserInput
//import com.benshapiro.composeexamples.base.rememberErrorHandlingInputState
//import com.benshapiro.composeexamples.navigation.ScreenEvent
//import com.benshapiro.composeexamples.toast
////import com.benshapiro.composeexamples.ui.MainScreen.FocusedTextFieldKey
//
//@SuppressLint("StateFlowValueCalledInComposition")
//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun DataInputScreen(
//    navController: NavController,
//    viewModel: DataInputScreenViewModel = viewModel(),
//) {
//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val focusManager = LocalFocusManager.current
//    val keyboardController = LocalSoftwareKeyboardController.current
//
//    val testFocusRequester = remember { FocusRequester() }
//
//    val events = remember(viewModel.events, lifecycleOwner) {
//        viewModel.events.flowWithLifecycle(
//            lifecycleOwner.lifecycle,
//            Lifecycle.State.STARTED
//        )
//    }
//    LaunchedEffect(Unit) {
//        events.collect { event ->
//            when (event) {
//                is ScreenEvent.ShowToast -> context.toast(event.messageId)
//                is ScreenEvent.UpdateKeyboard -> {
//                    if (event.show) keyboardController?.show() else keyboardController?.hide()
//                }
//                is ScreenEvent.ClearFocus -> focusManager.clearFocus()
////                is ScreenEvent.RequestFocus -> {
////                    when (event.textFieldKey) {
////                        FocusedTextFieldKey.TEST -> testFocusRequester.requestFocus()
////                        else -> {}
////                    }
////                }
//                is ScreenEvent.MoveFocus -> focusManager.moveFocus(event.direction)
//                is ScreenEvent.ClearInputs -> TODO()
//            }
//        }
//    }
//    Surface(
//        color = MaterialTheme.colors.primaryVariant,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//        ) {
//            rememberErrorHandlingInputState(
//                label = viewModel.testInput.value.labelText,
//                hint = viewModel.testInput.value.text,
//                errorMessage = null
//            )
//            ErrorHandlingUserInput(
//                modifier = Modifier
//                    .focusRequester(testFocusRequester)
//                    .onFocusChanged { focusState ->
//                        viewModel.onTextFieldFocusChanged(
//                            key = FocusedTextFieldKey.TEST,
//                            isFocused = focusState.isFocused
//                        )
//                    }
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//                    .padding(bottom = 8.dp),
//                state = viewModel.testInput,
//                onImeKeyAction = { /*TODO*/ },
//                onValueChange = viewModel.onTestInputEntered(),
//                keyboardOptions = remember {
//                    KeyboardOptions(
//                        keyboardType = KeyboardType.Text,
//                        imeAction = ImeAction.Next,
//                        capitalization = KeyboardCapitalization.Words
//                    )
//                },
//            )
//            ComposeExamplesButton(
//                buttonName = "Clear",
//                onClick = { viewModel.onClearClicked() }
//            )
//        }
//    }
//
//}