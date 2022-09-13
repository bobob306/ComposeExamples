package com.benshapiro.composeexamples.ui.AnotherScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.benshapiro.composeexamples.Screen
import com.benshapiro.composeexamples.base.ComposeExamplesButton

@Composable
fun AnotherScreen(navController: NavController) {
    Surface(
        color = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Center
        ) {
            Box()
            {
                Text(text = "Here is another screen")
            }
            ComposeExamplesButton(
                buttonName = "Return to main screen",
                onClick = {
                    navController.navigate(Screen.MainScreen.route)
                    Log.d("Navigation", "taking you safely home")
                },
            )
        }
    }
}