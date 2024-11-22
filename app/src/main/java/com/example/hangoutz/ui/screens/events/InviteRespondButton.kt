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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InviteRespondButton(
    backgroundColor: Color,
    fontColor: Color,
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = ButtonColors(
            containerColor = backgroundColor,
            contentColor = fontColor,
            disabledContainerColor = Color.Red,
            disabledContentColor = Color.Red
        ),
        modifier = Modifier
            .width(105.dp)
            .height(30.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                color = fontColor,
                fontWeight = FontWeight(400),
                fontSize = 13.sp
            )
        )
    }
}