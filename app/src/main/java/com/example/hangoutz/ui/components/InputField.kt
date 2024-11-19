package com.example.hangoutz.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.hangoutz.ui.theme.Error
import com.example.hangoutz.ui.theme.Ivory

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange : (String) -> Unit,
    isPassword: Boolean = false)
{

    OutlinedTextField(
        value = value,
        label = { Text(text = label, style = MaterialTheme.typography.bodySmall) },
        onValueChange = {onValueChange(it)},
        shape = RoundedCornerShape(20.dp),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Ivory,
            unfocusedTextColor = Ivory,
            cursorColor = Ivory,
            focusedLabelColor = Ivory,
            unfocusedLabelColor = Ivory,
            focusedBorderColor = Ivory,
            unfocusedBorderColor = Ivory,
            errorLabelColor = Ivory,
            errorBorderColor = Error,
            errorTextColor = Ivory
        ),

        textStyle = MaterialTheme.typography.bodySmall.copy(color = Color.White),
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
    )
}