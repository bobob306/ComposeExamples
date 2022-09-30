package com.benshapiro.composeexamples.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.benshapiro.composeexamples.model.Person
import com.benshapiro.composeexamples.ui.AnotherScreen.AnotherScreen
import com.benshapiro.composeexamples.ui.EditPersonScreen.EditPersonScreenViewModel
import com.benshapiro.composeexamples.ui.EditPersonScreen.EditPersonScreen
import com.benshapiro.composeexamples.ui.MainScreen.MainScreen
import com.benshapiro.composeexamples.ui.MainScreen.MainScreenViewModel
import com.benshapiro.composeexamples.ui.ViewUserScreen.ViewUserScreen
import com.benshapiro.composeexamples.ui.ViewUserScreen.ViewUserScreenViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route,
        ) {
            val viewModel = hiltViewModel<MainScreenViewModel>()
            MainScreen(
                navController = navController
            )
        }
        composable(
            route =  Screen.EditPersonScreen.route,
            arguments = listOf(
                navArgument("personId") {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                }
            ),
        ) { backStackEntry ->
            val viewModel = hiltViewModel<EditPersonScreenViewModel>()
            EditPersonScreen(
                navController = navController,
                viewModel = viewModel,
                personId = backStackEntry.arguments?.getString("personId") ?: "nothing"
                )

        }

        // Screen.EditPersonScreen.route
        composable(route = Screen.AnotherScreen.route) {
            AnotherScreen(navController = navController)
        }
        composable(route = Screen.ViewUserScreen.route) { backStackEntry ->
            val viewModel = hiltViewModel<ViewUserScreenViewModel>()
            ViewUserScreen(navController = navController)
        }

    }
}