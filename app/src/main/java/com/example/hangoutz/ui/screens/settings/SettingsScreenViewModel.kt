package com.example.hangoutz.ui.screens.settings

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.R
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.domain.repository.UserRepository
import com.example.hangoutz.utils.Constants.DEFAULT_USER_PHOTO
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsData(
    var name: String = "",
    var email: String = "",
    var isReadOnly: Boolean = true,
    val avatar: String? = null,
    val textIcon : Int = R.drawable.check
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsData())
    val uiState: StateFlow<SettingsData> = _uiState

    init {
        getUser()
    }

    fun onNameChanged(newText: String) {
        _uiState.value = _uiState.value.copy(name = newText)
    }

    fun onPencilClick(): Boolean {
        val isReadOnlyState = !_uiState.value.isReadOnly
        _uiState.value = _uiState.value.copy(isReadOnly = isReadOnlyState)

        if(_uiState.value.textIcon== R.drawable.pencil){
            _uiState.value = _uiState.value.copy(textIcon = R.drawable.check)
        }
        else _uiState.value = _uiState.value.copy(textIcon = R.drawable.pencil)

        return isReadOnlyState
    }

    fun logoutUser(onLogout: () -> Unit) {
        SharedPreferencesManager.clearUserId(context)
        onLogout()
    }

    private fun getUser() {
        val userID = SharedPreferencesManager.getUserId(context)
        viewModelScope.launch {
            try {
                val response =
                    userID?.let {
                        userRepository.getUserById(
                            it
                        )
                    }
                if (response?.isSuccessful == true && !response.body().isNullOrEmpty()
                ) {
                    val user = response.body()?.first()
                    user?.let {
                        _uiState.value = _uiState.value.copy(
                            name = it.name,
                            email = it.email,
                            avatar = it.avatar ?: DEFAULT_USER_PHOTO
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Login error: ${e.message}")
            }
        }
    }
}