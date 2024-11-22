package com.example.hangoutz.ui.components

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.hangoutz.ui.theme.Ivory


@Composable
fun nameField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        textStyle = TextStyle(
            color = Ivory,
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            ),
        readOnly = true
    )
}