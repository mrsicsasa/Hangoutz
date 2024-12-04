package com.example.hangoutz.ui.screens.friends

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.hangoutz.R
import com.example.hangoutz.data.models.Friend
import com.example.hangoutz.ui.components.DisplayUser
import com.example.hangoutz.ui.components.SearchField
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsPopup(
    userList: List<Friend>,
    searchQuery: String,
    isLoading: Boolean,
    sheetState: SheetState,
    showBottomSheet: (Boolean) -> Unit,
    clearText: () -> Unit,
    onTextInput: (String) -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            showBottomSheet(false)
            clearText()
        },
        modifier = Modifier
            .height(Dimensions.POPUP_HEIGHT)
            .width(Dimensions.POPUP_WIDTH)
            .semantics {
                contentDescription = Constants.BOTTOM_SHEET_TAG
            }
    ) {
        SearchField(
            searchQuery = searchQuery,
            backgroundColor = Color.Transparent,
            textColor = Color.Black,
            borderColor = Color.Black,
            clearText = clearText,
            onTextInput = onTextInput,
            modifier = Modifier
                .padding(horizontal = Dimensions.SEARCH_BAR_HORIZONTAL_PADDING)
                .semantics {
                    contentDescription = Constants.BOTTOM_SHEET_SEARCH
                }
        )
        if (isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(Dimensions.FRIENDS_LOADING_SPINNER_SIZE)
                        .semantics {
                            contentDescription = Constants.BOTTOM_SHEET_LOADING_SPINNER
                        })
            }
        } else if (userList.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (searchQuery.length >= Constants.MIN_SEARCH_LENGTH) {
                    Text(
                        text = stringResource(R.string.no_friends_found),
                        color = Color.Black,
                        modifier = Modifier.semantics {
                            contentDescription = Constants.NO_USERS_FOUND_MESSAGE
                        })
                } else {
                    Text(
                        text = stringResource(R.string.search_for_new_friends),
                        color = Color.Black,
                        modifier = Modifier.semantics {
                            contentDescription = Constants.BOTTOM_SHEET_SEARCH_MESSAGE
                        })
                }
            }
        }
        // Lista
        LazyColumn(
            modifier = Modifier
                .padding(
                    horizontal = Dimensions.BOTTOM_SHEET_HORIZONTAL_PADDING,
                    vertical = Dimensions.BOTTOM_SHEET_VERTICAL_PADDING
                )
        ) {
            items(userList) { user ->
                DisplayUser(user.name, user.avatar, isCheckList = false)
            }
        }
    }
}