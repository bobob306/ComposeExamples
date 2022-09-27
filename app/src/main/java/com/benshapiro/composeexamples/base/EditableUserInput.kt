package com.benshapiro.composeexamples.base

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.benshapiro.composeexamples.R

class EditableUserInputState(
    private val boxName: String? = null,
    private val hint: String? = null,
    initialText: String,
    private val savePressed: Boolean? = false,
    private val errorMessage: String? = null,
) {
    var text by mutableStateOf(initialText)
    val isHint: Boolean get() = text == hint
    var boxNameText by mutableStateOf(boxName)
    var isSavePressed by mutableStateOf(savePressed)
    var boxErrorMessage by mutableStateOf(errorMessage)

    companion object {
        val Saver: Saver<EditableUserInputState, *> = listSaver(
            save = { listOf(it?.boxName ?: "", it.hint, it.text, it.isSavePressed, it.errorMessage) }
        ) {
            EditableUserInputState(
                boxName = it[0] as String,
                hint = it[1] as String,
                initialText = it[2] as String,
                savePressed = it[3] as Boolean,
                errorMessage = it[4] as String,
            )
        }
    }
}

@Composable
fun rememberEditableUserInputState(boxName: String?, hint: String, savePressed: Boolean, errorMessage: String): EditableUserInputState =
    rememberSaveable(hint, saver = EditableUserInputState.Saver) {
        // make the initial text the same as the hint text
        EditableUserInputState(boxName, hint, hint, savePressed, errorMessage)
    }

@Composable
fun ComposeExamplesEditableTextUserInput(
    state: EditableUserInputState = rememberEditableUserInputState(boxName = "", hint = "", savePressed = false, errorMessage = ""),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        ComposeExamplesOutlinedTextField(state = state)
    }
}


@Composable
fun ComposeExamplesEditableTextUserInputOld(
    state: EditableUserInputState = rememberEditableUserInputState(boxName = "", hint = "", savePressed = false, errorMessage = ""),
) {
    ComposeExamplesBaseUserTextInput(
        modifier = Modifier.fillMaxWidth(),
        state = state
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed: Boolean by interactionSource.collectIsPressedAsState()
        if (isPressed) {
            if (state.isHint) state.text = ""
        }
        OutlinedTextField(
            value = state.text,
            onValueChange = { state.text = it },
            placeholder = { state.text },
            label = {
                Text(
                    text = if (state.boxErrorMessage == null)
                    { state.boxNameText ?: ""} else
                    { "${state.boxNameText} + ${state!!.boxErrorMessage}" }
                    ,
                    color = if (state.boxErrorMessage == null)
                    { Color.White } else { Color.Red }
                )
            },
            trailingIcon = {
                IconButton(onClick = { state.text = "" }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_clear_24),
                        contentDescription = "Clear text button"
                    )
                }
            },
            singleLine = true,
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
            )
    }
}

@Composable
fun ComposeExamplesEditableNumberUserInput(
    state: EditableUserInputState = rememberEditableUserInputState(boxName = "", hint = "", savePressed = false, errorMessage = ""),
) {
    Row(
        modifier = Modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ComposeExamplesOutlinedNumberField(state = state)
    }
}

@Composable
fun ComposeExamplesEditableNumberUserInputOld(
    state: EditableUserInputState = rememberEditableUserInputState(boxName = "", hint = "", savePressed = false, errorMessage = ""),
) {
    ComposeExamplesBaseUserNumberInput(
        modifier = Modifier.fillMaxWidth(),
        state = state
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed: Boolean by interactionSource.collectIsPressedAsState()
        if (isPressed) {
            if (state.isHint) state.text = ""
        }
        OutlinedTextField(
            value = state.text,
            onValueChange = { state.text = it },
            placeholder = { state.text },
            label = {
                Text(
                    text = state.boxNameText ?: "",
                    color = Color.White
                )
            },
            trailingIcon = {
                IconButton(onClick = { state.text = "" }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_clear_24),
                        contentDescription = "Clear text button"
                    )
                }
            },
            singleLine = true,
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            ),
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}

//            BasicTextField(
//                value = state.text,
//                onValueChange = { state.text = it },
//                textStyle = if (state.isHint) {
//                    captionTextStyle.copy(color = LocalContentColor.current)
//                } else {
//                    MaterialTheme.typography.body1.copy(color = LocalContentColor.current)
//                },
//                cursorBrush = SolidColor(LocalContentColor.current),
//                placeHolder
//            )

@Composable
fun ComposeExamplesEditableBoxInput(
    state: EditableUserInputState = rememberEditableUserInputState(boxName = "", hint = "", savePressed = false, errorMessage = ""),
    onClick: () -> Unit = { Log.d("Click", "registered") },
) {
    Row {
        Box(
            modifier = Modifier,
        ) {
            val interactionSource = remember { MutableInteractionSource() }
            val isPressed: Boolean by interactionSource.collectIsPressedAsState()
            if (isPressed) {
                if (state.isHint) state.text = ""
            }
            TextField(
                value = state.text,
                onValueChange = { state.text = it },
                singleLine = true,
                interactionSource = interactionSource,
//                modifier = Modifier.clickable(
//                    enabled = true,
//                    onClick = onClick
//                ),
            )
        }
    }
}

@Preview
@Composable
fun PreviewCEBoxInput() {
    val editableTestInputState = rememberEditableUserInputState(boxName = null, hint = "Text", savePressed = false, errorMessage = "")
    ComposeExamplesEditableBoxInput(
        state = editableTestInputState,
    )
}