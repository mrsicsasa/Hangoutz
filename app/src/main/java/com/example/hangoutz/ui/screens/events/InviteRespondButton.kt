package com.example.hangoutz.ui.screens.events

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions

@Composable
fun InviteRespondButton(
    backgroundColor: Color,
    fontColor: Color,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(Dimensions.INVITE_RESPOND_BUTTON_ROUNDED_RADIUS),
        colors = ButtonColors(
            containerColor = backgroundColor,
            contentColor = fontColor,
            disabledContainerColor = Color.Red,
            disabledContentColor = Color.Red
        ),
        modifier = modifier
            .width(Dimensions.INVITE_RESPOND_BUTTON_WIDTH)
            .height(Dimensions.INVITE_RESPOND_BUTTON_HEIGHT)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.displaySmall.copy(
                color = Color.White
            ),
            modifier = Modifier.semantics {
                contentDescription = Constants.INVITE_RESPOND_BUTTON_TEXT
            }
        )
    }
}