package com.benshapiro.composeexamples.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.benshapiro.composeexamples.Screen
import com.benshapiro.composeexamples.ui.AnotherScreen.AnotherScreen
import com.benshapiro.composeexamples.ui.MainScreen.MainScreen
import com.benshapiro.composeexamples.ui.ViewUserScreen.ViewUserScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route ) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.AnotherScreen.route) {
            AnotherScreen(navController = navController)
        }
        composable(route = Screen.ViewUserScreen.route) {
            ViewUserScreen(navController = navController)
        }
    }
}