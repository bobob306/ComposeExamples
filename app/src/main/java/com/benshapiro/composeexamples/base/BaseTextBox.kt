package com.benshapiro.composeexamples.base

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.benshapiro.composeexamples.model.Person
import com.benshapiro.composeexamples.ui.theme.ComposeExamplesTheme

@Composable
fun ComposeExamplesTextBox(
    text: String,
    lineHeight: TextUnit = 20.sp
){
    Box(modifier = Modifier
        .padding(all = 12.dp)
        .fillMaxWidth()
    ) {
        Text(
            text = text,
            lineHeight = lineHeight
        )
    }
}

@Composable
fun ComposeExamplesPersonTextBox(
    person: Person
) {
    Column(
        horizontalAlignment = CenterHorizontally
    ) {
        ComposeExamplesTextBox(
            text = "First name: ${person.firstName}",
            lineHeight = 16.sp
        )
        ComposeExamplesTextBox(
            text = "Last name: ${person.lastName}",
            lineHeight = 16.sp
        )
        ComposeExamplesTextBox(
            text = "Age: ${person.age}",
            lineHeight = 16.sp
        )
//
//        Box {
//           Text(text = "First name: ${person.firstName}")
//        }
//        Box {
//            Text(text = "Last name: ${person.lastName}")
//        }
//        Box {
//            Text(text = "Age: ${person.age}")
//        }
    }
}

@Preview
@Composable
private fun PreviewComposeExamplesTextBox(
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

@Preview
@Composable
private fun PreviewComposeExamplesPersonTextBox(){
    val testPerson = Person(
        "James",
        "Whicker",
        30)
    Surface(
        color = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.fillMaxSize()
    ) {
        ComposeExamplesPersonTextBox(person = testPerson)
    }

}