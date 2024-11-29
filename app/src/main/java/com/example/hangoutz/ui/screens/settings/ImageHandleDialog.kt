package com.example.hangoutz.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions

@Composable
fun ImageHandleDialog(
    onDismiss: () -> Unit,
    onPickFromGallery: () -> Unit,
    onCaptureFromCamera: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(Constants.SETTINGS_ACTION)
        },
        text = {
            Column {
                TextButton(onClick = onPickFromGallery) {
                    Text(Constants.SETTINGS_GALLERY)
                }
                Spacer(modifier = Modifier.height(Dimensions.SETTINGS_SCREEN_SMALL4))
                TextButton(onClick = onCaptureFromCamera) {
                    Text(Constants.SETTINGS_CAMERA)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}