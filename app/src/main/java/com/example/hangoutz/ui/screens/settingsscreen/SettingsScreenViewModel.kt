package com.example.hangoutz.ui.screens.settingsscreen

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.domain.repository.UserRepository
import com.example.hangoutz.ui.screens.loginscreen.LoginData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class LogoutData(
    var Name: String
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(LogoutData())
    val uiState: StateFlow<LogoutData> = _uiState

    fun changeName(newText: String){

        _uiState.value = _uiState.value.copy(Name = newText)
    }


    fun logoutUser(context : Context, onLogout: () -> Unit){
        SharedPreferencesManager.clearUserId(context)
        onLogout()

    }

}

