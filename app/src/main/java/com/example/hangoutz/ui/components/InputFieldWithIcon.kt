package com.example.hangoutz.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.hangoutz.ui.theme.Error
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.utils.Dimensions

@Composable
fun InputFieldWithIcon(
    label: String,
    value: String,
    onValueChange: (String) -> (Unit),
    modifier: Modifier = Modifier,
    painterResource: Int,
    isEnabled: Boolean = false,
    isReadOnly : Boolean = false,
    onClick: () -> (Unit)
) {
    OutlinedTextField(
        value = value,
        label = { Text(text = label, style = MaterialTheme.typography.bodySmall) },
        onValueChange = { onValueChange(it) },
        singleLine = true,
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
        trailingIcon = {
            androidx.compose.material3.Icon(
                painter = painterResource(id = painterResource),
                contentDescription = "Icon",
                tint = Color.White,
                modifier = Modifier.clickable { onClick() }
            )},
        textStyle = MaterialTheme.typography.bodySmall.copy(color = Color.White),
        modifier = modifier
            .padding(
                bottom = Dimensions.INPUT_FIELD_PADDING_SMALL,
                top = Dimensions.INPUT_FIELD_PADDING_SMALL,

            )
            .fillMaxWidth()
    )
}