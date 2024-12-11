package com.example.hangoutz.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.hangoutz.ui.theme.Error
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> (Unit),
    isError: Boolean,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(value = value,
        label = { Text(text = label, style = MaterialTheme.typography.bodySmall) },
        onValueChange = { onValueChange(it) },
        isError = isError,
        singleLine = true,
        shape = RoundedCornerShape(Dimensions.INPUT_FIELD_ROUNDED_CORNERS),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = when (label) {
                Constants.EMAIL -> KeyboardType.Email
                Constants.PASSWORD -> KeyboardType.Password
                else -> KeyboardType.Unspecified
            }
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
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
        modifier = modifier
            .padding(
                bottom = Dimensions.INPUT_FIELD_PADDING_SMALL,
                top = Dimensions.INPUT_FIELD_PADDING_SMALL
            )
            .fillMaxWidth()
    )
}

@Override
@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> (Unit),
    modifier: Modifier = Modifier,
    isEnabled: Boolean = false,
    isReadOnly: Boolean = false,
    isError: Boolean = false,
    singleLine: Boolean = true
) {

    OutlinedTextField(
        value = value,
        label = { Text(text = label, style = MaterialTheme.typography.bodySmall) },
        onValueChange = { onValueChange(it) },
        singleLine = singleLine,
        maxLines = Constants.MAX_LINES,
        isError = isError,
        enabled = isEnabled,
        readOnly = isReadOnly,
        shape = RoundedCornerShape(Dimensions.INPUT_FIELD_ROUNDED_CORNERS),
        colors = OutlinedTextFieldDefaults.colors(
            disabledBorderColor = Ivory,
            disabledTextColor = Ivory,
            disabledLabelColor = Ivory,
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
        modifier = modifier
            .padding(
                bottom = Dimensions.INPUT_FIELD_PADDING_SMALL,
                top = Dimensions.INPUT_FIELD_PADDING_SMALL,

                )
            .fillMaxWidth()
    )
}