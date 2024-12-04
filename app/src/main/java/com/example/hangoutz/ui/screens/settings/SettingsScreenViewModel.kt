package com.example.hangoutz.ui.screens.settings


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.BuildConfig
import com.example.hangoutz.R
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.domain.repository.UserRepository
import com.example.hangoutz.ui.components.getRandomString
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Constants.DEFAULT_USER_PHOTO
import com.example.hangoutz.utils.getTempUri
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject


data class SettingsData(
    var name: TextFieldValue = TextFieldValue(""),
    var email: String = "",
    var isReadOnly: Boolean = true,
    val avatar: String? = null,
    val avatarUri: String? = null,
    val textIcon: Int = R.drawable.pencil,
    val tempUri: Uri = Uri.EMPTY,
    val showBottomSheet: Boolean = false

)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsData())
    val uiState: StateFlow<SettingsData> = _uiState
    var tempUri: Uri = Uri.EMPTY

    init {
        getUser()
    }

    fun onNameChanged(newText: TextFieldValue) {
        _uiState.value = _uiState.value.copy(name = newText)
    }


    fun setShowBottomSheet(showBottomSheet: Boolean) {
        _uiState.value = _uiState.value.copy(showBottomSheet = showBottomSheet)
    }

    fun onPencilClick() {
        val currentText = _uiState.value.name.text
        _uiState.value = _uiState.value.copy(
            name = TextFieldValue(
                text = currentText.trimEnd().trimStart(), selection = TextRange(currentText.length)
            )
        )
        if (_uiState.value.name.text.length >= Constants.MIN_NAME_LENGTH && _uiState.value.name.text.length <= Constants.MAX_NAME_LENGTH) {
            val isReadOnlyState = !_uiState.value.isReadOnly
            val currentTextChanged = _uiState.value.name.text
            _uiState.value = _uiState.value.copy(
                name = TextFieldValue(
                    text = currentTextChanged, selection = TextRange(currentTextChanged.length)
                )
            )
            iconSwitch()
            saveName()

            _uiState.value = _uiState.value.copy(isReadOnly = isReadOnlyState)
        } else {
            Log.e("Name", "invalid input")
        }
    }

    private fun saveName() {
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

    private fun iconSwitch() {
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

    private fun handleImage(imageUri: Uri) {
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
                    val avatarNameGenerator = Constants.TEMPIMAGE + System.currentTimeMillis()
                        .toString() + getRandomString(Constants.RANDOM_STRING_LENGTH) + Constants.JPG

                    if (_uiState.value.avatar != DEFAULT_USER_PHOTO) {
                        val oldAvatar = _uiState.value.avatar ?: _uiState.value.avatarUri
                        if (!oldAvatar.isNullOrEmpty()) {
                            _uiState.value = _uiState.value.copy(avatar = avatarNameGenerator)
                            Log.d("Debug", "Attempting to delete avatar: $oldAvatar")
                            _uiState.value = _uiState.value.copy(avatar = avatarNameGenerator)
                            val deleteAvatarResponse =
                                userRepository.deleteUserAvatarByName(oldAvatar)
                            if (deleteAvatarResponse.isSuccessful) {
                                Log.i(
                                    "Info",
                                    "successfully deleted old avatar - " + deleteAvatarResponse.code()
                                )

                            } else {
                                Log.e(
                                    "Error",
                                    "An error has occurred" + deleteAvatarResponse.code()
                                )
                            }
                        }
                    }
                    val postAvatarResponse = userRepository.postAvatar(body, avatarNameGenerator)
                    if (postAvatarResponse.isSuccessful) {
                        _uiState.value = _uiState.value.copy(avatar = avatarNameGenerator)
                        userRepository.patchUserAvatarById(
                            userID ?: "", avatarNameGenerator
                        )
                        Log.e("Successfully edited", " ${postAvatarResponse.code()}")
                    } else {
                        Log.e("An error has occurred ", " ${postAvatarResponse.code()}")
                    }
                }
                Log.d("handleImage", inputStream.toString())
            }
        } catch (e: Exception) {
            Log.e("LoginViewModel", "Login error: ${e.message}")
        }
    }

    fun updateAvatarUri(uri: Uri) {

        viewModelScope.launch {
            val compressedUri = compressImage(context, uri)
            if (compressedUri != null) {
                _uiState.value = _uiState.value.copy(avatarUri = compressedUri.toString())
                handleImage(compressedUri)
            } else {
                Log.e("updateAvatarUri", "Failed to compress image")
            }
        }


    }

    fun setAvatarUri() {
        _uiState.value =
            _uiState.value.copy(avatarUri = "${BuildConfig.BASE_URL_AVATAR}${_uiState.value.avatar}")
    }

    fun requestCameraPermission(
        onPermissionGranted: () -> Unit,
        onPermissionDenied: (String) -> Unit
    ) {
        val permission = Manifest.permission.CAMERA
        if (ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            onPermissionGranted()
        } else {
            onPermissionDenied(permission)
        }
    }

    fun captureImage(onImageCaptured: (Uri) -> Unit) {
        val tmpUri = getTempUri(context)
        tempUri = tmpUri
        onImageCaptured(tempUri)
    }

    fun onPermissionResponse(
        isGranted: Boolean,
        onPermissionGranted: (Uri) -> Unit,
        onPermissionDenied: () -> Unit
    ) {
        if (isGranted) {
            val tmpUri = getTempUri(context)
            tempUri = tmpUri
            onPermissionGranted(tempUri)
        } else {
            onPermissionDenied()
        }
    }

    fun compressImage(context: Context, imageUri: Uri): Uri? {
        try {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            var quality = 100

            var resizedBitmap = originalBitmap
            do {
                val byteArrayOutputStream = ByteArrayOutputStream()
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
                val compressedSize = byteArrayOutputStream.size() / 1024

                if (compressedSize > 50) {
                    val scaleFactor = Math.sqrt(50.0 / compressedSize).toFloat()
                    val newWidth = (resizedBitmap.width * scaleFactor).toInt()
                    val newHeight = (resizedBitmap.height * scaleFactor).toInt()
                    resizedBitmap =
                        Bitmap.createScaledBitmap(resizedBitmap, newWidth, newHeight, true)
                } else {
                    break
                }

                quality -= 5
            } while (quality > 0)

            val tempFile =
                File(context.cacheDir, "compressed_image_${System.currentTimeMillis()}.jpg")
            val fileOutputStream = tempFile.outputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream)
            fileOutputStream.close()

            return Uri.fromFile(tempFile)
        } catch (e: Exception) {
            Log.e("compressImageTo50Kb", "Error compressing image: ${e.message}")
            return null
        }
    }
}

