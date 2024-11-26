package com.example.hangoutz.ui.screens.settings


import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.R
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.domain.repository.UserRepository
import com.example.hangoutz.utils.Constants.DEFAULT_USER_PHOTO
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
import java.io.InputStream
import javax.inject.Inject


data class SettingsData(
    var name: TextFieldValue = TextFieldValue(""),
    var email: String = "",
    var isReadOnly: Boolean = true,
    val avatar: String? = null,
    val avatarUri: String? = null,
    val textIcon: Int = R.drawable.pencil

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
                        Log.e("USPESNO IZMMENJEO", " ${response.code()}")
                    } else {
                        Log.e("Doslo je do greske ", " ${response.code()}")
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

    fun handleImage(imageUri: String, context: Context) {
        val uri = Uri.parse(imageUri)
        try {
            viewModelScope.launch {
                val userID = SharedPreferencesManager.getUserId(context)
                //val parsedUri = compressImageToByteArray(uri, context)
                val file = File(getRealPathFromURI(uri, context))
                val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file)
                val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
//                val parsedUri = userRepository.patchAvatarById(body)

                val response =

                    userRepository.patchAvatarById(body)



                if (response != null) {
                    if (response.isSuccessful) {
                        Log.e("USPESNO IZMMENJEO", " ${response.code()}")
                    } else {
                        Log.e("Doslo je do greske ", " ${response.code()}")
                        Log.e("Doslo je do greske ", " ${response}")
                    }
                }
            }
        } catch (e: Exception) {

            Log.e("LoginViewModel", "Login error: ${e.message}")

        }

    }


    fun updateAvatarUri(uri: String) {
        _uiState.value = _uiState.value.copy(avatarUri = uri)
        handleImage(uri, context)
    }

//
//    fun createvImg(){
//        val file = File(cacheDir, "img.png")
//        file.createNewFile()
//        file.outputStream().use {
//            assets.open("img.png").copyTo(it)
//        }
       // viewModel.uploadImage(file)

//    fun compressImageToByteArray(uri: Uri, context: Context) {
//        try {
//
//            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
//
//            // Decode the input stream into a Bitmap
//            val bitmap = BitmapFactory.decodeStream(inputStream)
//
//            // Prepare a ByteArrayOutputStream to hold the compressed image
//            val byteArrayOutputStream = ByteArrayOutputStream()
//
//            // Compress the Bitmap into JPEG format and write to the ByteArrayOutputStream
//           // val quality = 50 // Set the compression quality (0 to 100)
//           // bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
//
//            // Return the byte array of the compressed image
//            val byteArray= byteArrayOutputStream.toByteArray()
//            return byteArrayOutputStream.writeBytes()
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return null
//    }


    fun funkcija(uri : Uri , context: Context) {

        val file = File(getRealPathFromURI(uri, context))
        val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
    }

    fun getRealPathFromURI(uri: Uri, context: Context): String? {
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.let {
            it.moveToFirst()
            val columnIndex = it.getColumnIndex(MediaStore.Images.Media.DATA)
            if (columnIndex != -1) {
                return it.getString(columnIndex)
            }
        }
        return null
    }


}