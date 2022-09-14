package com.benshapiro.composeexamples.ui.AnotherScreen

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.benshapiro.composeexamples.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnotherScreenViewModel @Inject constructor
    (
) : ViewModel() {

    fun ReturnToMainScreen(navController: NavController){
        navController.navigate(Screen.MainScreen.route)
    }
}