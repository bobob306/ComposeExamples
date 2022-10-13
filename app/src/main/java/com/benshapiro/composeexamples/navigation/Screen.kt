package com.benshapiro.composeexamples.navigation

sealed class Screen (val route: String, val arguments: String? = null) {
    object MainScreen : Screen("main_screen")
    object AnotherScreen : Screen("another_screen")
    object ViewUserScreen : Screen("view_user_screen")
    object EditPersonScreen : Screen("edit_person_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
