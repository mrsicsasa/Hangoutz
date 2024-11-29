package com.example.hangoutz.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.hangoutz.R
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions

@Composable
fun SearchField(
    searchQuery: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    clearText: () -> Unit,
    onTextInput: (String) -> Unit
) {
    TextField(
        value = searchQuery,
        placeholder = {
            Text(
                text = stringResource(R.string.searchbar_placeholder),
                style = MaterialTheme.typography.bodySmall.copy(color = textColor)
            )
        },
        onValueChange = { onTextInput(it) },
        textStyle = MaterialTheme.typography.bodySmall.copy(color = textColor),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = backgroundColor,
            focusedContainerColor = backgroundColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(
                    onClick = clearText
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = Constants.FRIENDS_SEARCH_BAR_CLEAR_DESCRIPTION,
                        modifier = Modifier.size(Dimensions.FRIENDS_SEARCH_BAR_CLEAR_ICON_SIZE),
                        tint = textColor
                    )
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(Dimensions.FRIENDS_SEARCH_BAR_HEIGHT)
            .clip(RoundedCornerShape(Dimensions.FRIENDS_FIELD_ROUNDED_CORNER))
    )
}