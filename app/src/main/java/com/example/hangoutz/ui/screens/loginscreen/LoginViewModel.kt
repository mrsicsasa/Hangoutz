package com.example.hangoutz.ui.screens.loginscreen

import android.os.Build
import android.os.Message
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.data.remote.UserAPI
import com.example.hangoutz.data.repository.UserRepositoryImpl
import com.example.hangoutz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow

data class LoginData(
    var email: String = "",
    var password: String = "",
    val errorMessage: String = ""

)


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
//    private val apiService: UserRepository

) : ViewModel() {
//    var email by mutableStateOf("")
//    var password by mutableStateOf("")
//    var errorMessage by mutableStateOf("")
    private val _uiState = MutableStateFlow(LoginData())
    val uiState: StateFlow<LoginData> = _uiState


    @RequiresApi(Build.VERSION_CODES.O)
    fun userAuth(onLoginSuccess: () -> Unit) {

        if (_uiState.value.email == "" || uiState.value.password == "") {
            _uiState.value = _uiState.value.copy(errorMessage = "Email and password cannot be empty!")


        } else {
            viewModelScope.launch {


                try {
                    val response =
                        userRepository.getUserByEmailAndPassword(_uiState.value.email, hashPassword(_uiState.value.password))

                    //Log.e("LOGIN ATTEMPT --------------", " HASHED PASS ${hashPassword(password)}, ${response.code()} ")

                    if (response.isSuccessful && !response.body().isNullOrEmpty()
                        ) {
                         Log.e("LOGIN ATTEMPT --------", "SUCCESS")
                        onLoginSuccess()
                    } else {
                        _uiState.value = _uiState.value.copy(errorMessage = "Invalid credentials")
                        Log.e("RESPONSE CODE ------- ","${response.code()}")
                    }
                } catch (e: Exception) {

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