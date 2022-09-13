package com.benshapiro.composeexamples

sealed class Screen (val route: String) {
    object MainScreen : Screen("main_screen")
    object AnotherScreen : Screen("another_screen")
}
