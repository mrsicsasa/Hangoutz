package com.example.hangoutz.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File

fun getTempUri(context: Context): Uri {
    val directory = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        "MyAppImages"
    )
    directory.let {
        it.mkdirs()
        val file = try {
            Log.e("SettingsScreen", "File created")
            File.createTempFile(
                Constants.TEMPIMAGE + System.currentTimeMillis().toString(),
                Constants.JPG,
                it
            )
        } catch (e: Exception) {
            Log.e("SettingsScreen", "Failed to create temp file: ${e.message}")
            return Uri.EMPTY
        }
        return FileProvider.getUriForFile(
            context,
            "com.example.hangoutz.fileprovider",
            file
        )
    }
}