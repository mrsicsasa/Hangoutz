package com.example.hangoutz.ui.screens.settings

import android.content.Context
import android.net.Uri

sealed class Intent {
    data class OnImageSavedWith (val compositionContext: Context): Intent()
    data object OnImageSavingCanceled: Intent()
    data class OnFinishPickingImagesWith(val compositionContext: Context, val imageUrls: List<Uri>): Intent()
}