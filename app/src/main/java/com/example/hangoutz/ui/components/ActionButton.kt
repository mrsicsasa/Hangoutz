package com.example.hangoutz.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.hangoutz.ui.theme.Chestnut
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.utils.Dimensions

@Composable
fun ActionButton(
    buttonText: String,
    onClick: () -> (Unit)
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Ivory),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 50.dp, end = 50.dp)
            .height(50.dp),
    ) {
        Text(text = buttonText, style = MaterialTheme.typography.bodyMedium.copy(color = Chestnut))
    }
}

@Override
@Composable
fun ActionButton(
    painterResource: Int,
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = Dimensions.ACTION_BUTTON_MEDIUM2)
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Ivory, contentColor = Chestnut),
            shape = RoundedCornerShape(Dimensions.ACTION_BUTTON_MEDIUM3),
            modifier = modifier
                .fillMaxWidth()
                .padding(start = Dimensions.ACTION_BUTTON_MEDIUM3, end = Dimensions.ACTION_BUTTON_MEDIUM3)
                .height(Dimensions.ACTION_BUTTON_MEDIUM1)
                .align(Alignment.BottomCenter),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = buttonText,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(end = Dimensions.ACTION_BUTTON_SMALL1)
                )
                Icon(
                    painter = painterResource(painterResource),
                    contentDescription = "Icon",
                    modifier = Modifier.size(Dimensions.ACTION_BUTTON_MEDIUM3)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}