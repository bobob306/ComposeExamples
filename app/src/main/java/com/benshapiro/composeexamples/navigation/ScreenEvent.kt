package com.benshapiro.composeexamples.navigation

import androidx.compose.ui.focus.FocusDirection
import com.benshapiro.composeexamples.ui.MainScreen.FocusedTextFieldKey

sealed class ScreenEvent{
    class ShowToast(val messageId: String) : ScreenEvent()
    class UpdateKeyboard(val show: Boolean) : ScreenEvent()
    class RequestFocus(val textFieldKey: FocusedTextFieldKey) : ScreenEvent()
    object ClearFocus : ScreenEvent()
    class MoveFocus(val direction: FocusDirection = FocusDirection.Down) : ScreenEvent()
    object ClearInputs : ScreenEvent()
}
