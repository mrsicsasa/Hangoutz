package com.example.hangoutz.ui.screens.settings


import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.BuildConfig
import com.example.hangoutz.R
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.domain.repository.UserRepository
import com.example.hangoutz.ui.components.getRandomString
import com.example.hangoutz.utils.Constants.DEFAULT_USER_PHOTO
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject


data class SettingsData(
    var name: TextFieldValue = TextFieldValue(""),
    var email: String = "",
    var isReadOnly: Boolean = true,
    val avatar: String? = null,
    val avatarUri: String? = null,
    val textIcon: Int = R.drawable.pencil,
    val currentCameraUri: Uri? = null

)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsData())
    val uiState: StateFlow<SettingsData> = _uiState

    init {
        getUser()
    }

    fun onNameChanged(newText: TextFieldValue) {
        _uiState.value = _uiState.value.copy(name = newText)
    }

    fun onPencilClick() {
        val currentText = _uiState.value.name.text
        _uiState.value = _uiState.value.copy(
            name = TextFieldValue(
                text = currentText.trimEnd().trimStart(), selection = TextRange(currentText.length)
            )
        )
        if (_uiState.value.name.text.length >= 3 && _uiState.value.name.text.length <= 25) {
            val isReadOnlyState = !_uiState.value.isReadOnly
            val currentText = _uiState.value.name.text
            _uiState.value = _uiState.value.copy(
                name = TextFieldValue(
                    text = currentText, selection = TextRange(currentText.length)
                )
            )
            iconSwitch()
            saveName()

            _uiState.value = _uiState.value.copy(isReadOnly = isReadOnlyState)
        } else {
            Log.e("Name", "invalid input")
        }
    }

    fun saveName() {
        if (!_uiState.value.isReadOnly) {
            viewModelScope.launch {
                val userID = SharedPreferencesManager.getUserId(context)
                val newName = _uiState.value.name

                val response = userID?.let {
                    userRepository.patchUserNameById(it, newName.text)

                }
                if (response != null) {
                    if (response.isSuccessful) {
                        Log.e("Successfully  edited", " ${response.code()}")
                    } else {
                        Log.e("An error has occurred ", " ${response.code()}")
                    }
                }
            }
        }
    }

    fun iconSwitch() {
        if (_uiState.value.textIcon == R.drawable.pencil) {
            _uiState.value = _uiState.value.copy(textIcon = R.drawable.checkmark)
        } else _uiState.value = _uiState.value.copy(textIcon = R.drawable.pencil)
    }

    fun logoutUser(onLogout: () -> Unit) {
        SharedPreferencesManager.clearUserId(context)
        onLogout()
    }

    private fun getUser() {
        val userID = SharedPreferencesManager.getUserId(context)
        viewModelScope.launch {
            try {
                val response = userID?.let {
                    userRepository.getUserById(
                        it
                    )
                }
                if (response?.isSuccessful == true && !response.body().isNullOrEmpty()) {
                    val user = response.body()?.first()
                    user?.let {
                        _uiState.value = _uiState.value.copy(
                            name = TextFieldValue(it.name),
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

    fun handleImage(imageUri: Uri) {
        try {
            viewModelScope.launch {

                val userID = SharedPreferencesManager.getUserId(context)
                val inputStream = context.contentResolver.openInputStream(imageUri)
                inputStream?.let {
                    val requestFile = RequestBody.create(
                        MediaType.parse("image/jpeg"), it.readBytes()
                    )
                    val body = MultipartBody.Part.createFormData(
                        "file", "image_${System.currentTimeMillis()}.jpg", requestFile
                    )
                    val avatarNameGenerator = getRandomString(20) + ".jpg"
                    if (_uiState.value.avatar != "avatar_default.png") {
                        val response3 =
                            userRepository.deleteUserAvatarByName(_uiState.value.avatar ?: "")
                        if (response3.isSuccessful) {
                            Log.i("Info", "sucessfuly deleted old avatar - " + response3.code())
                        } else {
                            Log.e("Error", "An error has occured" + response3.code())
                        }
                    }
                    val response = userRepository.postAvatar(body, avatarNameGenerator)
                    if (response != null) {
                        if (response.isSuccessful) {
                            val response2 = userRepository.patchUserAvatarById(
                                userID ?: "", avatarNameGenerator
                            )
                            Log.e("Successfully edited", " ${response.code()}")
                        } else {
                            Log.e("An error has ocurred ", " ${response.code()}")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("LoginViewModel", "Login error: ${e.message}")
        }
    }

    fun updateAvatarUri(uri: Uri) {
        _uiState.value = _uiState.value.copy(avatarUri = uri.toString())
        handleImage(uri)
    }

    fun setAvatarUri() {
        _uiState.value =
            _uiState.value.copy(avatarUri = "${BuildConfig.BASE_URL_AVATAR}${_uiState.value.avatar}")

    }
}
