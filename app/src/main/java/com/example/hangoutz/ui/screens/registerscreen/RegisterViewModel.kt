package com.example.hangoutz.ui.screens.registerscreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.R
import com.example.hangoutz.data.models.UserRequest
import com.example.hangoutz.domain.repository.UserRepository
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
            Fields.CONFIRMPASSWORD -> _uiState.value = _uiState.value.copy(confirmPassword = newText)
        }
    }

    fun onCreateAccountClick(context: Context, onRegisterSuccess: () -> (Unit)) {
        if (registerValidation(context)) {
            register(context, onRegisterSuccess)
        }
    }

    private fun registerValidation(context: Context): Boolean {
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
                incompleteFormError = context.getString(R.string.all_fields_must_be_filled)
            )
            isValid = false
        } else {
            if (!Validator.isValidNameLength(_uiState.value.name)) {
                _uiState.value = _uiState.value.copy(nameError = context.getString(R.string.name_error_message))
                isValid = false
            }

            if (!Validator.isValidEmail(_uiState.value.email)) {
                _uiState.value = _uiState.value.copy(emailError = context.getString(R.string.email_format_error_message))
                isValid = false
            }

            if (!Validator.isValidPassword(_uiState.value.password)) {
                _uiState.value =
                    _uiState.value.copy(passwordError = context.getString(R.string.password_error_message))
                isValid = false
            }

            if (!Validator.doPasswordsMatch(_uiState.value.password, _uiState.value.confirmPassword)) {
                _uiState.value = _uiState.value.copy(confirmPasswordError = context.getString(R.string.confirmPassword_error_message))
                isValid = false
            }
        }
        return isValid
    }

    private fun register(context: Context, onRegisterSuccess: () -> (Unit)) {
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
                    _uiState.value = _uiState.value.copy(emailError = context.getString(R.string.email_duplicate_error_message))
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(incompleteFormError = context.getString(R.string.generic_error_message))
                Log.e("LoginViewModel", "Login error: ${e.message}")
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