package com.example.hangoutz.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.TextFieldValue
import com.example.hangoutz.utils.Constants.SETTINGS_NAME_FIELD_TAG

@Composable
fun NameField(
    name: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
    isReadOnly: Boolean,
    focusRequester: FocusRequester,
    modifier: Modifier

) {
    BasicTextField(
        value = name,
        onValueChange = onTextChange,
        textStyle = MaterialTheme.typography.headlineLarge,
        readOnly = isReadOnly,
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .semantics { contentDescription = SETTINGS_NAME_FIELD_TAG }
    )
}