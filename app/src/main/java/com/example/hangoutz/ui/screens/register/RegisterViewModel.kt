package com.example.hangoutz.ui.screens.registerscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.data.models.UserRequest
import com.example.hangoutz.domain.repository.UserRepository
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.HashPassword
import com.example.hangoutz.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val nameError: String = "",
    val emailError: String = "",
    val passwordError: String = "",
    val confirmPasswordError: String = "",
    val incompleteFormError: String = "",

    val isValidated: Boolean = false
)

enum class Fields {
    NAME,
    EMAIL,
    PASSWORD,
    CONFIRMPASSWORD
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
            Fields.EMAIL -> _uiState.value = _uiState.value.copy(email = newText.trim())
            Fields.PASSWORD -> _uiState.value = _uiState.value.copy(password = newText)
            Fields.CONFIRMPASSWORD -> _uiState.value =
                _uiState.value.copy(confirmPassword = newText)
        }
    }

    fun onCreateAccountClick(onRegisterSuccess: () -> (Unit)) {
        if (registerValidation()) {
            register(onRegisterSuccess)
        }
    }

    private fun registerValidation(): Boolean {
        var isValid = true
        clearErrors()
        _uiState.value = _uiState.value.copy(isValidated = true)
        val errors = Validator.areAllFieldsFilled(
            _uiState.value.name,
            _uiState.value.email,
            _uiState.value.password,
            _uiState.value.confirmPassword
        )
        if (errors.contains(true)) {
            _uiState.value = _uiState.value.copy(
                incompleteFormError = Constants.ERROR_EMPTY_FIELDS
            )
            isValid = false
        } else {
            if (!Validator.isValidNameLength(_uiState.value.name)) {
                _uiState.value =
                    _uiState.value.copy(nameError = Constants.NAME_ERROR_MESSAGE)
                isValid = false
            }

            if (!Validator.isValidEmail(_uiState.value.email)) {
                _uiState.value =
                    _uiState.value.copy(emailError = Constants.EMAIL_FORMAT_ERROR_MESSAGE)
                isValid = false
            }

            if (!Validator.isValidPassword(_uiState.value.password)) {
                _uiState.value =
                    _uiState.value.copy(passwordError = Constants.PASSWORD_ERROR_MESSAGE)
                isValid = false
            }

            if (!Validator.doPasswordsMatch(
                    _uiState.value.password,
                    _uiState.value.confirmPassword
                )
            ) {
                _uiState.value =
                    _uiState.value.copy(confirmPasswordError = Constants.CONFIRM_PASSWORD_ERROR_MESSAGE)
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
                } else if (response.code() == Constants.DUPLICATE_ITEM) {
                    _uiState.value =
                        _uiState.value.copy(emailError = Constants.EMAIL_DUPLICATE_ERROR_MESSAGE)
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(incompleteFormError = Constants.GENERIC_ERROR_MESSAGE)
                Log.e("RegisterViewModel", "Register error: ${e.message}")
            }
        }
    }

    private fun clearErrors() {
        _uiState.value = _uiState.value.copy(
            nameError = "",
            emailError = "",
            passwordError = "",
            confirmPasswordError = "",
            incompleteFormError = ""
        )
    }
}