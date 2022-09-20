package com.benshapiro.composeexamples.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.benshapiro.composeexamples.OnImeKeyAction
import com.benshapiro.composeexamples.OnValueChange
import com.benshapiro.composeexamples.model.InputWrapper

@Composable
fun CustomTextField(
    modifier: Modifier,
    inputWrapper: InputWrapper,
    labelResId: String,
    keyboardOptions: KeyboardOptions = remember {
        KeyboardOptions.Default
    },
    onValueChange: OnValueChange,
    onImeKeyAction: OnImeKeyAction

) {
    val fieldValue = remember {
        mutableStateOf(TextFieldValue(inputWrapper.value, TextRange(inputWrapper.value.length)))
    }
    Column {
        TextField(
            modifier = modifier,
            value = fieldValue.value,
            onValueChange = {
                fieldValue.value = it
                onValueChange(it.text)
            },
            label = { Text((labelResId)) },
            isError = inputWrapper.errorId != null,
            keyboardOptions = keyboardOptions,
            keyboardActions = remember {
                KeyboardActions(onAny = { onImeKeyAction() })
            },
        )
        if (inputWrapper.errorId != null) {
            Text(
                text = inputWrapper.errorId,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}