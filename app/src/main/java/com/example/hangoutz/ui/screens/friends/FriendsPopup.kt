package com.example.hangoutz.ui.screens.friends

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import com.example.hangoutz.R
import com.example.hangoutz.data.models.Friend
import com.example.hangoutz.ui.components.DisplayUser
import com.example.hangoutz.ui.components.SearchField
import com.example.hangoutz.ui.theme.CoolGray
import com.example.hangoutz.ui.theme.OrangeButton
import com.example.hangoutz.ui.theme.SilverCloud
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsPopup(
    userList: List<Friend>,
    searchQuery: String,
    isLoading: Boolean,
    sheetState: SheetState,
    showBottomSheet: (Boolean) -> Unit,
    clearText: () -> Unit,
    addFriend: (UUID) -> Unit = {},
    onChange: (isChecked: Boolean, user: Friend) -> Unit = { _: Boolean, _: Friend -> },
    isParticipant: Boolean = false,
    isCheckList: Boolean = false,
    onAdd: () -> Unit = {},
    participantSelected: List<Friend> = emptyList(),
    onRemove: (Friend) -> Unit = {},
    onTextInput: (String) -> Unit,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            showBottomSheet(false)
            clearText()
        },
        containerColor = SilverCloud,
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
        } else if (isCheckList && searchQuery.length < Constants.MIN_SEARCH_LENGTH) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(R.string.search_icon),
                    modifier = Modifier
                        .size(Dimensions.CREATE_EVENT_SEARCH_ICON_SIZE),
                    contentScale = ContentScale.Fit
                )
            }

        } else if (userList.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (searchQuery.length >= Constants.MIN_SEARCH_LENGTH) {
                    Text(
                        text = stringResource(R.string.no_friends_found),
                        color = CoolGray,
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
        LazyColumn(
            modifier = Modifier
                .padding(
                    horizontal = Dimensions.BOTTOM_SHEET_HORIZONTAL_PADDING,
                    vertical = Dimensions.BOTTOM_SHEET_VERTICAL_PADDING
                )
                .fillMaxHeight(if (isCheckList) Dimensions.FRIENDS_POPUP_PARTICIPANT_COLUMN else Dimensions.FRIENDS_POPUP_COLUMN)
        ) {
            items(userList) { user ->
                DisplayUser(
                    user.name,
                    user.avatar,
                    isCheckList = isCheckList,
                    isParticipant = isParticipant,
                    isCheckedInitial = participantSelected.any { it.id == user.id },
                    onChange = { onChange(it, user) },
                    addFriend = { addFriend(user.id) },
                    onRemove = { onRemove(user) }
                )
            }
        }
        if (isCheckList) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = Dimensions.BUTTON_COLUMN_BOTTOM_PADDING)
            ) {
                Button(
                    onClick = { onAdd() },
                    modifier = Modifier
                        .padding(Dimensions.BUTTON_INNER_PADDING)
                        .width(Dimensions.BUTTON_WIDTH)
                        .height(Dimensions.BUTTON_HEIGHT)
                        .semantics {
                            contentDescription =
                                Constants.CREATE_EVENT_ADD_SELECTED_PARTICIPANTS_BUTTON
                        },
                    colors = ButtonColors(
                        containerColor = OrangeButton,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Gray
                    )
                ) {
                    Text(
                        stringResource(R.string.add),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight(Dimensions.ADD_FONT_WEIGHT),
                            fontSize = Dimensions.ADD_FONT_SIZE,
                        )
                    )
                }
            }
        }
    }
}