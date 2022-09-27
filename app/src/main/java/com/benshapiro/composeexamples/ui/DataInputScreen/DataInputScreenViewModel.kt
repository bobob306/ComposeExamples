package com.benshapiro.composeexamples.ui.DataInputScreen
//
//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.benshapiro.composeexamples.InputValidator
//import com.benshapiro.composeexamples.base.ErrorHandlingInputState
//import com.benshapiro.composeexamples.navigation.ScreenEvent
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.channels.Channel
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.receiveAsFlow
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//const val TEST = "test"
//
//enum class FocusedTextFieldKey {
//    TEST, NONE
//}
//
//@HiltViewModel
//class DataInputScreenViewModel @Inject constructor(
//    private val handle: SavedStateHandle
//) : ViewModel() {
//
//    private val _events = Channel<ScreenEvent>()
//    val events = _events.receiveAsFlow()
//    // sealed class to handle all one time events
//
//    private var focusedTextField = handle.get("focusedTextField") ?: FocusedTextFieldKey.TEST
//        set(value) {
//            field = value
//            handle.set("focusedTextField", value)
//        }
//    // store name of last focused field, first name is top so is default if nothing known
//
////    init {
////        if (focusedTextField != FocusedTextFieldKey.NONE) focusOnLastSelectedTextField()
////    }
//    // start by showing the last known/default field, and show the right kind of keyboard
//
//    fun onTextFieldFocusChanged(key: FocusedTextFieldKey, isFocused: Boolean) {
//        focusedTextField = if (isFocused) key else FocusedTextFieldKey.NONE
//    }
//    // gets called when composables have focus events and clears when unfocused events happen
//
//    private suspend fun clearFocusAndHideKeyboard() {
//        _events.send(ScreenEvent.ClearFocus)
//        _events.send(ScreenEvent.UpdateKeyboard(false))
//        focusedTextField = FocusedTextFieldKey.NONE
//    }
//
////    private fun focusOnLastSelectedTextField() {
////        viewModelScope.launch(Dispatchers.Default) {
////            _events.send(ScreenEvent.RequestFocus(focusedTextField))
////            delay(250)
////            _events.send(ScreenEvent.UpdateKeyboard(true))
////        }
////    }
//
//    val testInput = handle.getStateFlow(TEST, ErrorHandlingInputState("Test field", "Enter text"))
//    fun onTestInputEntered() {
//        val errorId = testInput.value.text?.let { InputValidator.getTestErrorOrNull(it) }
//        testInput.value.errorMessageLabel = errorId
//    }
//
//    fun onClearClicked() {
//        testInput.value.text = ""
//    }
//
//}