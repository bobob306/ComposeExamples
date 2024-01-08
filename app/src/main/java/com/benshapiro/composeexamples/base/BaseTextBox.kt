package com.benshapiro.composeexamples.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Start
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
) {
    Box(
        modifier = Modifier
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
    person: Person,
    deleteOnClick: () -> Unit,
    onEditClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .border(
                border =
                BorderStroke(
                    width = 2.dp,
                    color = Color.White
                )
            )
            .fillMaxWidth()
    )
    {
        Box(
            Modifier
                .fillMaxWidth()
                .align(TopEnd),
            TopEnd,
        ) {
            DeleteButton {deleteOnClick()}
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            horizontalArrangement = Start,
        ) {
            Column()
            {
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
                ComposeExamplesTextBox(
                    text = "Phone number: ${person.phoneNumber}",
                    lineHeight = 16.sp
                )
            }
        }
        Box(
            Modifier
                .fillMaxWidth()
                .align(BottomEnd),
            BottomEnd,
        )
        {
            EditButton {
                onEditClick()
            }
        }
    }
}

@Preview
@Composable
private fun PreviewComposeExamplesTextBox(
    text: String = "some text",
    lineHeight: TextUnit = 24.sp
) {
    ComposeExamplesTheme {
        Surface(
            Modifier
                .fillMaxWidth(),
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
private fun PreviewComposeExamplesPersonTextBox() {
    val testPerson = Person(
        id = "egg",
        "James",
        "Whicker",
        30,
        "07512671574",

    )
    Surface(
        color = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.fillMaxWidth()
    ) {
        ComposeExamplesPersonTextBox(person = testPerson, {}, {})
    }

}