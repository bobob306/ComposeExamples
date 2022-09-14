package com.benshapiro.composeexamples.ui.AnotherScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.benshapiro.composeexamples.base.ComposeExamplesButton
import com.benshapiro.composeexamples.base.ComposeExamplesTextBox

@Composable
fun AnotherScreen(
    navController: NavController,
    viewModel: AnotherScreenViewModel = viewModel()
) {
    Surface(
        color = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Center
        ) {
            ComposeExamplesTextBox(
                lineHeight = 40.sp,
                text = "Here is another screen" +
                        "\nIf you press the back button your text will still be there" +
                        "\nOtherwise if you click Return to main screen, it will be gone???",
            )
            ComposeExamplesButton(
                buttonName = "Return to main screen",
                onClick = {
                    viewModel.ReturnToMainScreen(navController)
                    Log.d("Navigation", "taking you safely home")
                },
            )
        }
    }
}