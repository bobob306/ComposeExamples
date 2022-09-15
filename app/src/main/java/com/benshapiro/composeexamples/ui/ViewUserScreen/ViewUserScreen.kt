package com.benshapiro.composeexamples.ui.ViewUserScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.benshapiro.composeexamples.base.CircularProgressBar
import com.benshapiro.composeexamples.base.ComposeExamplesPersonTextBox
import com.benshapiro.composeexamples.base.ComposeExamplesTextBox

@Composable
fun ViewUserScreen(
    navController: NavController,
    viewModel: ViewUserScreenViewModel = viewModel()
) {
//    val viewModel: ViewUserScreenViewModel = viewModel()

    val persons = viewModel.personList.value.data

    Surface(
        color = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.fillMaxSize(),
    ) {
        persons?.let {
            LazyColumn(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(all = 16.dp)
            ) {
                items(
                    items = persons
                ) { person ->
                    ComposeExamplesPersonTextBox(person = person)
                }
            }
        }
    }

    val e = viewModel.dataOrException.e
    e?.let {
        ComposeExamplesTextBox(text = e.message!!)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally
    ) {
        CircularProgressBar(
            isDisplayed = viewModel.loading.value
        )
    }
}
//
//}val listState: LazyListState = rememberLazyListState()
//    val personList: List<Person> = viewModel.personList.value
//    Surface(
//        color = MaterialTheme.colors.primaryVariant,
//        modifier = Modifier.fillMaxSize()
//    ){
//        LazyColumn(
//            state = listState
//        ) {
//            Log.d("list size", personList.size.toString())
//            items(personList) { personItem ->
//                Log.d("person #${personItem[]}", "is ${personItem.firstName}")
//                Column(
//                    Modifier.fillParentMaxWidth()
//                ) {
//                    ComposeExamplesPersonTextBox(person = personItem)
//                }
//            }
//        }
//    }