package com.example.hangoutz.ui.screens.settings


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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SettingsScreen(navController: NavController, viewmodel: SettingsViewModel = hiltViewModel()) {
    val data = viewmodel.uiState.collectAsState()
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let {
                viewmodel.setShowImageCropper(it, true)
            }
        }
    )
    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isSaved ->
            if (isSaved) {
                viewmodel.tempUri.let {
                    viewmodel.setShowImageCropper(it, true)
                }
            } else {
                viewmodel.setAvatarUri()
            }
        }
    )
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        viewmodel.onPermissionResponse(
            isGranted,
            onPermissionGranted = { uri ->
                takePhotoLauncher.launch(uri)
            },
            onPermissionDenied = {
                viewmodel.setAvatarUri()
            }
        )
    }

    if (data.value.showImageCropper) {
        ImageCropperScreen(
            bitmap = viewmodel.uriToBitmap(LocalContext.current, data.value.tempUri),
            onBitmapCropped = { croppedBitmap ->
                viewmodel.updateBitmap(croppedBitmap)
            }
        )
        //viewmodel.updateAvatarUri()
    } else {
        if (data.value.showBottomSheet) {
            ImageHandleDialog(
                onDismiss = { viewmodel.setShowBottomSheet(false) },
                onCaptureFromCamera = {
                    viewmodel.setShowBottomSheet(false)
                    viewmodel.requestCameraPermission(
                        onPermissionGranted = {
                            viewmodel.captureImage { uri ->
                                takePhotoLauncher.launch(uri)
                            }
                        },
                        onPermissionDenied = { permission ->
                            cameraPermissionLauncher.launch(permission)
                        }
                    )
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
                        .semantics {
                            contentDescription = SETTINGS_BACKGROUND_LINES_TAG
                        },
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
}