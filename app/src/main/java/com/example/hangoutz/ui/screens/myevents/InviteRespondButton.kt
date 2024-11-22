package com.example.hangoutz.ui.screens.myevents

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
        modifier = Modifier.background(backgroundColor)
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