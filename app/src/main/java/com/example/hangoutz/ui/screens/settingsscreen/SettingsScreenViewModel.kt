package com.example.hangoutz.ui.screens.settingsscreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsData(
    var name: String = "",
    var email: String = "",
    val avatar: String? = null
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsData())
    val uiState: StateFlow<SettingsData> = _uiState

    fun onNameChanged(newText: String) {
        _uiState.value = _uiState.value.copy(name = newText)
    }

    fun logoutUser(context: Context, onLogout: () -> Unit) {
        SharedPreferencesManager.clearUserId(context)
        onLogout()
    }

    fun getUser(context: Context) {

        val userID = SharedPreferencesManager.getUserId(context)
        viewModelScope.launch {
            try {
                val response =
                    userRepository.getUserById(
                        userID!!,
                    )
                if (response.isSuccessful && !response.body().isNullOrEmpty()
                ) {
                    val user = response.body()?.first()
                    user?.let {

                        _uiState.value = _uiState.value.copy(
                            name = it.name,
                            email = it.email,
                            avatar = if (it.avatar == null) "avatar_default.png" else it.avatar
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Login error: ${e.message}")
            }
        }
    }
}