package com.example.hangoutz.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag

@Composable
fun nameField(
    text: String,
    onTextChange: (String) -> Unit,
    isReadOnly: Boolean,
    focusRequester: FocusRequester,
    modifier: Modifier

) {
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        textStyle = MaterialTheme.typography.displayMedium,
        readOnly = isReadOnly,
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .testTag("nameField")
    )
}