package com.example.hangoutz.ui.screens.settings


import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hangoutz.BuildConfig
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.ActionButton
import com.example.hangoutz.ui.components.NameInput
import com.example.hangoutz.ui.navigation.NavigationItem
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.utils.Constants.LOGOUT
import com.example.hangoutz.utils.Constants.PROFILE_PHOTO
import com.example.hangoutz.utils.Constants.SETTINGS_BACKGROUND_LINES_TAG
import com.example.hangoutz.utils.Constants.SETTINGS_EMAIL_FIELD_TAG
import com.example.hangoutz.utils.Constants.SETTINGS_LOGOUT_BUTTON
import com.example.hangoutz.utils.Constants.SETTINGS_USER_PHOTO_TAG
import com.example.hangoutz.utils.Dimensions
import com.example.hangoutz.utils.getTempUri

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SettingsScreen(navController: NavController, viewmodel: SettingsViewModel = hiltViewModel()) {
    val data = viewmodel.uiState.collectAsState()
    val context = LocalContext.current
    var tempUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let {
                viewmodel.updateAvatarUri(it)
            }
        }
    )
    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isSaved ->
            if (isSaved) {
                tempUri?.let {
                    viewmodel.updateAvatarUri(it)
                }
            } else {
                viewmodel.setAvatarUri()
            }
        }
    )
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val tmpUri = getTempUri(context)
            tempUri = tmpUri
            tempUri?.let { takePhotoLauncher.launch(it) }
        } else {
            viewmodel.setAvatarUri()
        }
    }

    if (data.value.showBottomSheet) {
        ImageHandleDialog(
            onDismiss = {viewmodel.setShowBottomSheet(false)},
            onCaptureFromCamera = {
                viewmodel.setShowBottomSheet(false)
                val permission = Manifest.permission.CAMERA
                if (ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val tmpUri = getTempUri(context)
                    tempUri = tmpUri
                    tempUri?.let { takePhotoLauncher.launch(it) }
                } else {

                    cameraPermissionLauncher.launch(permission)
                }
            },
            onPickFromGallery = {
                viewmodel.setShowBottomSheet(false)
                imagePicker.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
        )
    }
    Column(
        modifier = Modifier
            .padding(top = Dimensions.SETTINGS_SCREEN_MEDIUM2)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(Dimensions.SETTINGS_SCREEN_LARGE1)
                    .clip(CircleShape)
                    .border(Dimensions.SETTINGS_SCREEN_SMALL3, Ivory, CircleShape)
            ) {}
            GlideImage(
                model = data.value.avatarUri
                    ?: "${BuildConfig.BASE_URL_AVATAR}${data.value.avatar}",
                contentDescription = PROFILE_PHOTO,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(Dimensions.SETTINGS_SCREEN_LARGE2)
                    .height(Dimensions.SETTINGS_SCREEN_LARGE2)
                    .clip(CircleShape)
                    .align(Alignment.Center)
                    .testTag(SETTINGS_USER_PHOTO_TAG)
                    .clickable {
                        viewmodel.setShowBottomSheet(true)
                    }
            )
            Image(
                painter = painterResource(R.drawable.profilelines),
                contentDescription = "lines",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.Center)
                    .testTag(SETTINGS_BACKGROUND_LINES_TAG),
                colorFilter = ColorFilter.tint(Ivory)
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .height(Dimensions.SETTINGS_SCREEN_MEDIUM1),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.SETTINGS_SCREEN_SMALL1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NameInput(
                    data.value.textIcon,
                    data.value.name,
                    data.value.isReadOnly,
                    { viewmodel.onNameChanged(it) },
                    { viewmodel.onPencilClick() },
                )
            }
            Text(
                text = data.value.email,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(end = Dimensions.SETTINGS_SCREEN_SMALL4)
                    .align(Alignment.CenterHorizontally)
                    .testTag(SETTINGS_EMAIL_FIELD_TAG)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .align(Alignment.End)
                .padding(bottom = Dimensions.SETTINGS_SCREEN_SMALL2)
        ) {
            ActionButton(R.drawable.iconlogout, LOGOUT, onClick = {
                viewmodel.logoutUser {
                    navController.navigate(NavigationItem.Login.route) {
                        popUpTo(0)
                    }
                }
            }, modifier = Modifier.testTag(SETTINGS_LOGOUT_BUTTON))
        }
    }
}