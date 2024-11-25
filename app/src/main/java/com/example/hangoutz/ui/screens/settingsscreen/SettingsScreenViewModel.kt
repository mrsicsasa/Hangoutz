package com.example.hangoutz.ui.screens.settingsscreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.R
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
    var isReadOnly : Boolean = true,
    val avatar: String? = null,
    val textIcon : Int = R.drawable.pencil
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsData())
    val uiState: StateFlow<SettingsData> = _uiState

    init {
        getUser(context)
    }

    fun onNameChanged(newText: String) {
        _uiState.value = _uiState.value.copy(name = newText)
    }

    fun onPencilClick() : Boolean{
     val state =  !_uiState.value.isReadOnly
        _uiState.value = _uiState.value.copy(isReadOnly = state)

        if(_uiState.value.textIcon== R.drawable.pencil){
            _uiState.value = _uiState.value.copy(textIcon = R.drawable.check)
        }
        else _uiState.value = _uiState.value.copy(textIcon = R.drawable.pencil)

        //Log.e("LOGG------ ","STATE = ${state} & ISREADONLY ${_uiState.value.isReadOnly}")

        return state
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