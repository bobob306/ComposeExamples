package com.benshapiro.composeexamples.ui.EditPersonScreen

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benshapiro.composeexamples.data.DataOrException
import com.benshapiro.composeexamples.model.Person
import com.benshapiro.composeexamples.module.AppModule
import com.benshapiro.composeexamples.repository.PersonsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPersonScreenViewModel @Inject constructor(
    private val repository: PersonsRepository,
    private val handle: SavedStateHandle,
) : ViewModel() {

    val dataOrException = AppModule.provideDataOrException()

    var loading = mutableStateOf(false)

    val handlePersonId = handle.get<String>("personId").toString()

    private var _editPerson: MutableState<DataOrException<Person, Exception>> =
        mutableStateOf(
            DataOrException(
                Person(),
                Exception("")
            )
        )
    val editPerson get() = _editPerson

    init {
        val handlePersonId = handle.get<String>("personId").toString()
        Log.d("handle", handle.get<String>("personId").toString())
        if (handlePersonId != "nothing") {
        getPersonById(handlePersonId) }

    }

    private fun getPersonById(handlePersonId : String) {
        viewModelScope.launch {
            loading.value = true
            _editPerson.value = repository.getPersonFromFirestore(handlePersonId)
            Log.d("person =", editPerson.value.data.toString())
            loading.value = false
        }
    }


}