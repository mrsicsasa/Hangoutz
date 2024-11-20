package com.example.hangoutz.ui.screens.registerscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.data.models.UserRequest
import com.example.hangoutz.domain.repository.UserRepository
import com.example.hangoutz.utils.HashPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirm: String = "",

    val nameError: String = "",
    val emailError: String = "",
    val passwordError: String = "",
    val confirmError: String = "",
    val incompleteError: String = "",

    val isValidated: Boolean = false
)

enum class Fields {
    NAME,
    EMAIL,
    PASSWORD,
    CONFIRM
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun onTextChanged(field: Fields, newText: String) {
        when (field) {
            Fields.NAME -> _uiState.value = _uiState.value.copy(name = newText)
            Fields.EMAIL -> _uiState.value = _uiState.value.copy(email = newText)
            Fields.PASSWORD -> _uiState.value = _uiState.value.copy(password = newText)
            Fields.CONFIRM -> _uiState.value = _uiState.value.copy(confirm = newText)
        }
    }

    fun onCreateAccountClick(onRegisterSuccess: () -> (Unit)) {
        if (userAuth()) {
            register { onRegisterSuccess }
        }
    }

    fun userAuth(): Boolean {
        var isValid = true
        clearErrors()
        _uiState.value = _uiState.value.copy(isValidated = true)
        val errors = Validator.areAllFieldsFilled(
            _uiState.value.name,
            _uiState.value.email,
            _uiState.value.password,
            _uiState.value.confirm
        )
        if (errors.contains(true)) {
            _uiState.value = _uiState.value.copy(
                incompleteError = "All fields must be filled!"
            )
            isValid = false
        } else {
            if (!Validator.isValidNameLength(_uiState.value.name)) {
                _uiState.value = _uiState.value.copy(nameError = "Name must be 3-25 characters")
                isValid = false
            }

            if (!Validator.isValidEmail(_uiState.value.email)) {
                _uiState.value = _uiState.value.copy(emailError = "Email must be in correct format")
                isValid = false
            }

            if (!Validator.isValidPassword(_uiState.value.password)) {
                _uiState.value =
                    _uiState.value.copy(passwordError = "Password must contain at least one number and be minimum 8 characters long")
                isValid = false
            }

            if (!Validator.doPasswordsMatch(_uiState.value.password, _uiState.value.confirm)) {
                _uiState.value = _uiState.value.copy(confirmError = "Password must match")
                isValid = false
            }
        }
        return isValid
    }

    private fun register(onRegisterSuccess: () -> (Unit)) {
        viewModelScope.launch {
            try {
                val response = userRepository.insertUser(
                    UserRequest(
                        _uiState.value.name, null, _uiState.value.email,
                        HashPassword.hashPassword(_uiState.value.password)
                    )
                )
                if (response.isSuccessful) {
                    onRegisterSuccess()
                } else if (response.code() == 409) {
                    _uiState.value = _uiState.value.copy(emailError = "Email already in use")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(incompleteError = "An error has occurred")
                Log.e("LoginViewModel", "Login error: ${e.message}")
            }
        }
    }

    private fun clearErrors() {
        _uiState.value = _uiState.value.copy(
            nameError = "",
            emailError = "",
            passwordError = "",
            confirmError = "",
            incompleteError = ""
        )
    }
}