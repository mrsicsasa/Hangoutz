package com.example.hangoutz.ui.screens.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.hangoutz.R
import com.example.hangoutz.ui.theme.DeleteColor
import com.example.hangoutz.utils.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Color.Transparent
        SwipeToDismissBoxValue.EndToStart -> DeleteColor
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(Dimensions.FRIENDS_FIELD_ROUNDED_CORNER))
            .background(color)
            .padding(
                top = Dimensions.FRIENDS_OUTER_PADDING,
                bottom = Dimensions.FRIENDS_OUTER_PADDING,
                end = Dimensions.DISMISS_ICON_END_PADDING
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.Right
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = stringResource(R.string.delete)
        )
    }
}