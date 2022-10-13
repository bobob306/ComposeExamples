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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.benshapiro.composeexamples.base.CircularProgressBar
import com.benshapiro.composeexamples.base.ComposeExamplesButton
import com.benshapiro.composeexamples.base.ComposeExamplesPersonTextBox
import com.benshapiro.composeexamples.base.ComposeExamplesTextBox
import com.benshapiro.composeexamples.model.Person

@Composable
fun ViewUserScreen(
    navController: NavController,
    viewModel: ViewUserScreenViewModel = viewModel(),
) {

    Surface(
        color = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column() {
            ComposeExamplesButton(
                buttonName = "Return home",
                onClick = { viewModel.returnHome(navController) }
            )
            viewModel.personList.value.let {
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .fillMaxSize()
                ) {
                    items(
                        items = viewModel.personList.value.data!!
                    ) { person ->
                        ComposeExamplesPersonTextBox(
                            person = person,
                            deleteOnClick = { viewModel.deletePerson(person) },
                            onEditClick = {viewModel.onEditClick(person, navController)}
                        )
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
}