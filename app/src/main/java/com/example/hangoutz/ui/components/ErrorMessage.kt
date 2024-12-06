package com.example.hangoutz.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.hangoutz.ui.theme.Error
import com.example.hangoutz.utils.Dimensions


@Composable
fun ErrorMessage(errorMessage: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(
                start = Dimensions.ERROR_MESSAGE_PADDING_SMALL,
                end = Dimensions.ERROR_MESSAGE_PADDING_SMALL
            )
            .fillMaxWidth()

    ) {
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyMedium,
            color = Error
        )
    }
}