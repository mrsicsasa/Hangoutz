package com.example.hangoutz.ui.screens.settings


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.content.FileProvider
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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


data class SettingsData(
    var name: TextFieldValue = TextFieldValue(""),
    var email: String = "",
    var isReadOnly: Boolean = true,
    val avatar: String? = null,
    val avatarUri: String? = null,
    val textIcon: Int = R.drawable.pencil,
    val currentCameraUri : Uri? = null

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

        if (_uiState.value.name.text.length >= 3 && _uiState.value.name.text.length <= 25) {
            val isReadOnlyState = !_uiState.value.isReadOnly

            val currentText = _uiState.value.name.text

            _uiState.value = _uiState.value.copy(
                name = TextFieldValue(
                    text = currentText,
                    selection = TextRange(currentText.length)
                )
            )
            iconSwitch()
            saveName()

            _uiState.value = _uiState.value.copy(isReadOnly = isReadOnlyState)
        } else {
            Log.e("IME ", " NIJE ISPRAVAN UNOS")
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
                //   val imagePath = getRealPathFromURI(imageUri, context)
                //

                val userID = SharedPreferencesManager.getUserId(context)
//                val file = getRealPathFromURI(imageUri, context)?.let { File(it) }
                val inputStream = context.contentResolver.openInputStream(imageUri)
                inputStream?.let {
                    // Read the bytes from the InputStream and create the RequestBody
                    val requestFile = RequestBody.create(
                        MediaType.parse("image/jpeg"),
                        it.readBytes() // Read bytes from the InputStream
                    )
//                val requestFile = file?.let {
//                    RequestBody.create(
//                        MediaType.parse("image/jpeg"),
//                        it
//                    )

                val body = MultipartBody.Part.createFormData("file", "image_${System.currentTimeMillis()}.jpg", requestFile)

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
                val response =
                    userRepository.postAvatar(body, avatarNameGenerator)
                if (response != null) {
                    if (response.isSuccessful) {
                        val response2 =
                            userRepository.patchUserAvatarById(userID ?: "", avatarNameGenerator)
                        Log.e("Sucessfuly edited", " ${response.code()}")
                    } else {
                        Log.e("An error has ocurred ", " ${response.code()}")
                    }
                }
            }
        } }catch (e: Exception) {
            Log.e("LoginViewModel", "Login error: ${e.message}")
        }
    }


    fun testUpd(imageUri: Uri) {

        _uiState.value = _uiState.value.copy(avatarUri = imageUri.toString())
        Log.e("AVATARURI", " ${_uiState.value.avatar}")
        Log.e("AVATARURI", " ${_uiState.value.avatarUri}")



        handleImage(imageUri)

    }

    fun onReceive(intent: Intent) = viewModelScope.launch(coroutineContext) {
        when (intent) {
            is Intent.OnFinishPickingImagesWith -> { /* TODO */
            }

            is Intent.OnImageSavedWith -> { /* TODO */
            }

            is Intent.OnImageSavingCanceled -> { /* TODO */
            }
        }

    }

    fun updateAvatarUri(uri: Uri) {

        _uiState.value = _uiState.value.copy(avatarUri = uri.toString())
        handleImage(uri)
    }

    fun getRealPathFromURI(contentUri: Uri, context: Context): String? {

        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(contentUri, proj, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            val filePath = cursor.getString(columnIndex)
            cursor.close()
            return filePath
        }
        return null
    }

    fun getInputStreamFromUri(uri: Uri): InputStream? {
        return try {
            context.contentResolver.openInputStream(uri)  // Open the InputStream directly
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        }
    }
}
