package com.benshapiro.composeexamples.base

import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.benshapiro.composeexamples.OnImeKeyAction
import com.benshapiro.composeexamples.OnValueChange
import com.benshapiro.composeexamples.R
import kotlinx.coroutines.flow.StateFlow


class ErrorHandlingInputState(
    private val label: String? = null,
    private val hint: String? = null,
    initialText: String? = hint ?: "",
    private val errorMessage: String? = null,
) {
    var text by mutableStateOf(initialText)
    val isHint: Boolean get() = text == hint
    var labelText by mutableStateOf(label)
    var errorMessageLabel by mutableStateOf(errorMessage)

    companion object {
        val Saver: Saver<ErrorHandlingInputState, *> = listSaver(
            save = {
                listOf(
                    it?.label,
                    it?.hint,
                    it?.text,
                    it?.errorMessage,
                )
            }
        ) {
            ErrorHandlingInputState(
                label = it[0] as String?,
                hint = it[1] as String?,
                initialText = it[2] as String?,
                errorMessage = it[3] as String?,
            )
        }
    }
}

@Composable
fun rememberErrorHandlingInputState(
    label: String?,
    hint: String?,
    errorMessage: String?,
): ErrorHandlingInputState =
    rememberSaveable(hint, saver = ErrorHandlingInputState.Saver) {
        // make the initial text the same as the hint text
        ErrorHandlingInputState(label, hint, hint, errorMessage)
    }

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ErrorHandlingUserInput(
    modifier: Modifier,
    state: StateFlow<ErrorHandlingInputState>,
    length: Int = state.value.text!!.length,
    onImeKeyAction: OnImeKeyAction,
    onValueChange: (value: String) -> Unit,
    keyboardOptions: KeyboardOptions = remember {
        KeyboardOptions.Default
    },
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()
    if (isPressed) {
        if (state.value.isHint) state.value.text = ""
    }
    Column {
        OutlinedTextField(
            singleLine = true,
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp),
            value = state.value.text!!,
            onValueChange = {
                state.value.text = it
                onValueChange(it)
            },
            isError = state.value.errorMessageLabel != null,
            keyboardOptions = keyboardOptions,
            placeholder = { state.value.text },
            label = {
                Text(
                    text = state.value.labelText ?: "",
                    color = if (state.value.errorMessageLabel == null) {
                        Color.White
                    } else {
                        Color.Red
                    }
                )
            },
            trailingIcon = {
                IconButton(onClick = { state.value.text = "" }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_clear_24),
                        contentDescription = "Clear text button"
                    )
                }
            },
        )
        if (state.value.errorMessageLabel != null) {
            state.value.errorMessageLabel.let {
                Text(
                    text = state.value.errorMessageLabel!!,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}