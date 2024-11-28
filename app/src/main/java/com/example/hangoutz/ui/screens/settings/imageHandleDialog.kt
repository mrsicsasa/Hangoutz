package com.example.hangoutz.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@Composable
fun imageHandleDialog(
    onDismiss: () -> Unit,
    onPickFromGallery: () -> Unit,
    onCaptureFromCamera: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Choose Action")
        },
        text = {
            Column {
                TextButton(onClick = onPickFromGallery) {
                    Text("Pick from Gallery")
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = onCaptureFromCamera) {
                    Text("Capture from Camera")
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