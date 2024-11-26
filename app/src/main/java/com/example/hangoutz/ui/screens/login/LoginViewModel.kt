package com.example.hangoutz.ui.screens.login

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.domain.repository.UserRepository
import com.example.hangoutz.utils.Constants.ERROR_EMPTY_FIELDS
import com.example.hangoutz.utils.Constants.ERROR_INVALID_INPUT
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject

data class LoginData(
    var email: String = "",
    var password: String = "",
    val errorMessage: String = "",
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginData())
    val uiState: StateFlow<LoginData> = _uiState

    @RequiresApi(Build.VERSION_CODES.O)
    fun userAuth(onLoginSuccess: () -> Unit) {
        _uiState.value = _uiState.value.copy(isEmailError = false, isPasswordError = false)
        if (_uiState.value.email.isEmpty() || _uiState.value.password.isEmpty()) {
            val emailEmpty = _uiState.value.email.isEmpty()
            val passwordEmpty = _uiState.value.password.isEmpty()
            _uiState.value = _uiState.value.copy(
                isEmailError = emailEmpty,
                isPasswordError = passwordEmpty,
                errorMessage = ERROR_EMPTY_FIELDS
            )
        } else {
            _uiState.value = _uiState.value.copy(
                isPasswordError = false,
                isEmailError = false,
                errorMessage = ""
            )
            viewModelScope.launch {
                try {
                    val response =
                        userRepository.getUserByEmailAndPassword(
                            _uiState.value.email,
                            hashPassword(_uiState.value.password)
                        )
                    if (response.isSuccessful && !response.body().isNullOrEmpty()
                    ) {
                        val user = response.body()?.first()
                        user?.let {
                            SharedPreferencesManager.saveUserId(context, it.id.toString())
                        }
                        _uiState.value =
                            _uiState.value.copy(isPasswordError = false, isEmailError = false)
                        onLoginSuccess()
                    } else {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = ERROR_INVALID_INPUT,
                            isEmailError = true,
                            isPasswordError = true
                        )
                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(errorMessage = "An error has occurred")
                    Log.e("LoginViewModel", "Login error: ${e.message}")
                }
            }
        }
    }

    private fun hashPassword(password: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = messageDigest.digest(password.toByteArray())
        return hashedBytes.joinToString("") { "%02x".format(it) }
    }

    fun onTextChanged(newText: String) {
        _uiState.value = _uiState.value.copy(email = newText, isEmailError = false)
    }

    fun onPassChanged(newText: String) {
        _uiState.value = _uiState.value.copy(password = newText, isPasswordError = false)
    }
}