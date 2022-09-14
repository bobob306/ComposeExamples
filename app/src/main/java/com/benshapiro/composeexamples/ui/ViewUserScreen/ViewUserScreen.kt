package com.benshapiro.composeexamples.ui.ViewUserScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.benshapiro.composeexamples.base.ComposeExamplesPersonTextBox
import com.benshapiro.composeexamples.base.ComposeExamplesTextBox
import com.benshapiro.composeexamples.model.Person

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ViewUserScreen(
    navController: NavController,
    viewModel: ViewUserScreenViewModel = viewModel(),
) {
    val listState: LazyListState = rememberLazyListState()
    val personList: List<Person> = viewModel.personList.value
    Surface(
        color = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.fillMaxSize()
    ){
        LazyColumn(
            state = listState
        ) {
            Log.d("list size", personList.size.toString())
            items(personList) { personItem ->
                Log.d("person #${personItem[]}", "is ${personItem.firstName}")
                Column(
                    Modifier.fillParentMaxWidth()
                ) {
                    ComposeExamplesPersonTextBox(person = personItem)
                }
            }
        }
    }
}