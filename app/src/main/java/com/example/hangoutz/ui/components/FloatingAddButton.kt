package com.example.hangoutz.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.hangoutz.R
import com.example.hangoutz.ui.theme.PurpleDark
import com.example.hangoutz.utils.Dimensions

@Composable
fun FloatingPlusButton(modifier: Modifier, onClickAction: () -> (Unit)) {
    FloatingActionButton(
        onClick = onClickAction,
        modifier = modifier
            .padding(Dimensions.FRIENDS_FLOATING_BUTTON_PADDING)
            .border(Dimensions.FLOATING_BUTTON_BORDER, PurpleDark, CircleShape)
            .clip(CircleShape)
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.floating_action_button_icon_description),
            modifier = Modifier.size(Dimensions.FLOATING_ICON_SIZE)
        )
    }
}