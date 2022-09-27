package com.benshapiro.composeexamples.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.benshapiro.composeexamples.OnImeKeyAction
import com.benshapiro.composeexamples.OnSaveClicked
import com.benshapiro.composeexamples.OnValueChange
import com.benshapiro.composeexamples.R
import com.benshapiro.composeexamples.model.InputWrapper

@Composable
fun ComposeExamplesTextField(
    modifier: Modifier,
    inputWrapper: InputWrapper,
    labelResId: String,
    keyboardOptions: KeyboardOptions = remember {
        KeyboardOptions.Default
    },
    onValueChange: OnValueChange,
    onImeKeyAction: OnImeKeyAction,
    onSaveClicked: OnSaveClicked

) {
    var fieldValue = remember {
        mutableStateOf(
            TextFieldValue(
                inputWrapper.value,
                TextRange(inputWrapper.value.length)
            // check the text is suitable length to be a name longer than one letter
            )
        )
    }
    Column {
        OutlinedTextField(
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
            trailingIcon = {
                IconButton(onClick = { fieldValue.value = fieldValue.value.copy(text = "")}) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_clear_24),
                        contentDescription = "Clear text button"
                    )
                }
            },
            singleLine = true,
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