package com.benshapiro.composeexamples.base

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.benshapiro.composeexamples.ui.theme.ComposeExamplesTheme

@Composable
fun ClickableTextField(){
    val text =
        rememberEditableUserInputState(null, hint = "Enter some text")
    TextField(
        value = text.text,
        onValueChange = { text.text = it },
        modifier = Modifier.clickable(
            enabled = true,
            onClick = { onClick(text) },
        ),
    )
}

fun onClick(text: EditableUserInputState) {
    Log.d("click", "registered")
    if (text.isHint) {
        text.text = ""
    }
}

@Preview
@Composable
fun PreviewClickableText(){
    ComposeExamplesTheme() {
        ClickableTextField()
    }
}
