package com.example.hangoutz.ui.screens.friends

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.hangoutz.ui.components.SearchField
import com.example.hangoutz.utils.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsPopup(
    searchQuery: String,
    sheetState: SheetState,
    showBottomSheet: (Boolean) -> Unit,
    clearText: () -> Unit,
    onTextInput: (String) -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        modifier = Modifier
            .height(Dimensions.POPUP_HEIGHT)
            .width(Dimensions.POPUP_WIDTH),
        onDismissRequest = {
            showBottomSheet(false)
        }
    ) {
        SearchField(
            searchQuery = searchQuery,
            backgroundColor = Color.Transparent,
            textColor = Color.Black,
            clearText = clearText
        ) {
            onTextInput(searchQuery)
        }
    }
}