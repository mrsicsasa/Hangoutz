package com.example.hangoutz.ui.screens.loginscreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject

data class LoginData(
    var email: String = "",
    var password: String = "",
    val errorMessage: String = ""
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginData())
    val uiState: StateFlow<LoginData> = _uiState

    @RequiresApi(Build.VERSION_CODES.O)
    fun userAuth(onLoginSuccess: () -> Unit) {
        if (_uiState.value.email == "" || uiState.value.password == "") {
            _uiState.value =
                _uiState.value.copy(errorMessage = "All fields must be filled!")
        } else {
            viewModelScope.launch {
                try {
                    val response =
                        userRepository.getUserByEmailAndPassword(
                            _uiState.value.email,
                            hashPassword(_uiState.value.password)
                        )
                    if (response.isSuccessful && !response.body().isNullOrEmpty()
                    ) {
                        onLoginSuccess()
                    } else {
                        _uiState.value = _uiState.value.copy(errorMessage = "Incorrect email or password")
                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(errorMessage = "An error has occurred")
                    Log.e("LoginViewModel", "Login error: ${e.message}")
                }
            }
        }
    }
    fun hashPassword(password: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = messageDigest.digest(password.toByteArray())
        return hashedBytes.joinToString("") { "%02x".format(it) }
    }
    fun onTextChanged(newText: String) {
        _uiState.value = _uiState.value.copy(email = newText)
    }

    fun onPassChanged(newText: String) {
        _uiState.value = _uiState.value.copy(password = newText)
    }

}