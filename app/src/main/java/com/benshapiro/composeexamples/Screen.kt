package com.benshapiro.composeexamples

sealed class Screen (val route: String) {
    object MainScreen : Screen("main_screen")
    object AnotherScreen : Screen("another_screen")
    object ViewUserScreen : Screen("view_user_screen")
//    object DataInputScreen : Screen("data_input_screen")
}
