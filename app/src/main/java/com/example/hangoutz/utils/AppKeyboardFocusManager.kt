package com.example.hangoutz.utils

import KeyBoardManager
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.focus.FocusManager

@Composable
fun AppKeyboardFocusManager(
    focusManager: ProvidableCompositionLocal<FocusManager>,
    context: ProvidableCompositionLocal<Context>
) {
    val context = context.current
    val focusManager = focusManager.current
    DisposableEffect(key1 = context) {
        val keyboardManager = KeyBoardManager(context)
        keyboardManager.attachKeyboardDismissListener {
            focusManager.clearFocus()
        }
        onDispose {
            keyboardManager.releaseKeyboardDismissListener()
        }
    }
}
