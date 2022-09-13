package com.benshapiro.composeexamples.base

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.benshapiro.composeexamples.R

@Composable
fun ComposeExamplesBaseUserTextInput(
    state: EditableUserInputState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    caption: String? = null, // describes the box on the left, e.g. input name box
    showCaption: () -> Boolean = { true }, // decides if the caption should be shown
    tint: Color = LocalContentColor.current,
    content: @Composable () -> Unit,
) {
        Row(
            Modifier.padding(all = 12.dp),
        ) {
            ComposeExamplesOutlinedTextField(state)
        }
}

@Composable
fun ComposeExamplesBaseUserNumberInput(
    state: EditableUserInputState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    caption: String? = null, // describes the box on the left, e.g. input name box
    showCaption: () -> Boolean = { true }, // decides if the caption should be shown
    tint: Color = LocalContentColor.current,
    content: @Composable () -> Unit,
) {
    Row(
        Modifier.padding(all = 12.dp),
    ) {
        ComposeExamplesOutlinedNumberField(state)
    }
}

@Composable
fun ComposeExamplesOutlinedTextField(state: EditableUserInputState) {
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp)
    )
}

@Composable
fun ComposeExamplesOutlinedNumberField(state: EditableUserInputState) {
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
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp)
    )
}