package com.example.stateflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel : ViewModel() {

    private  var uiStateFlow : MutableStateFlow<UiStates> = MutableStateFlow(UiStates.EmptyState)
    val stateFlow : StateFlow<UiStates> = uiStateFlow

     fun loginCredentials(userName : String , passWord : String) = viewModelScope.launch {
       uiStateFlow.value = UiStates.Loading
        delay(2000)
        if(userName == "Android" && passWord == "TopSecret") {
            uiStateFlow.value = UiStates.Success
        } else {
            uiStateFlow.value = UiStates.Error("Exception Caught ...")
        }
    }

    sealed class UiStates(){
        // TODO : Sealed Class is a class that is inherited from with inner classes only
        object Success : UiStates()
        data class Error(var errorMsg : String) : UiStates()
        object Loading : UiStates()
        object EmptyState : UiStates()

    }


}