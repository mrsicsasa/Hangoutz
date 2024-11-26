package com.example.hangoutz.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.hangoutz.ui.theme.Ivory
import androidx.compose.ui.platform.testTag
import com.example.hangoutz.utils.Constants.SETTINGS_NAME_FIELD_TAG

@Composable
fun nameField(
    name : TextFieldValue,
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
            .testTag(SETTINGS_NAME_FIELD_TAG)
    )
}