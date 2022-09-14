package com.benshapiro.composeexamples.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.benshapiro.composeexamples.ui.theme.ComposeExamplesTheme

@Composable
fun ComposeExamplesTextBox(
    text: String,
    lineHeight: TextUnit = 20.sp
){
    Box(modifier = Modifier
        .padding(all = 12.dp)) {
        Text(
            text = text,
            lineHeight = lineHeight
        )
    }
}

@Preview
@Composable
fun PreviewComposeExamplesTextBox(
    text: String = "some text",
    lineHeight: TextUnit = 24.sp
){
    ComposeExamplesTheme {
        Surface(
            Modifier
                .fillMaxSize(),
            color = MaterialTheme.colors.primaryVariant
        ) {
            Column {
                ComposeExamplesTextBox(
                    text = text,
                    lineHeight = lineHeight
                )
            }
        }
    }
}