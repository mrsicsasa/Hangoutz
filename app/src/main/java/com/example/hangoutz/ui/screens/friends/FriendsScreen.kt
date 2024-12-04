package com.example.hangoutz.ui.screens.friends

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.FloatingPlusButton
import com.example.hangoutz.ui.components.SearchField
import com.example.hangoutz.ui.theme.CoolGray
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions

@Composable
fun FriendsScreen(viewModel: FriendsViewModel = hiltViewModel()) {
    val data = viewModel.uiState.collectAsState()
    if (data.value.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(Dimensions.FRIENDS_LOADING_SPINNER_SIZE)
        ) {
            CircularProgressIndicator(modifier = Modifier.semantics { Constants.FRIENDS_LOADING_SPINNER })
        }
    } else if (data.value.listOfFriends.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.no_friends_found),
                color = Color.LightGray,
                modifier = Modifier.semantics {
                    contentDescription = Constants.NO_FRIENDS_AVAILABLE_MESSAGE
                })
        }
    }
    Box(
        modifier = Modifier
            .padding(Dimensions.FRIENDS_OUTER_PADDING)
            .semantics {
                contentDescription = Constants.FRIENDS_BACKGROUND_BOX
            }
    ) {
        Column {
            SearchField(
                searchQuery = data.value.searchQuery,
                textColor = Ivory,
                backgroundColor = CoolGray,
                clearText = { viewModel.clearSearchInput() },
                modifier = Modifier
                    .padding(
                        start = Dimensions.FRIENDS_HORIZONTAL_PADDING,
                        end = Dimensions.FRIENDS_HORIZONTAL_PADDING,
                        top = Dimensions.FRIENDS_OUTER_PADDING,
                        bottom = Dimensions.FRIENDS_OUTER_PADDING
                    )
                    .semantics {
                        contentDescription = Constants.FRIENDS_SEARCH_BAR
                    }) { searchQuery ->
                viewModel.onSearchInput(searchQuery)
            }
            if (data.value.listOfFriends.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(
                            start = Dimensions.FRIENDS_HORIZONTAL_PADDING,
                            end = Dimensions.FRIENDS_HORIZONTAL_PADDING,
                            top = Dimensions.FRIENDS_FIELD_VERTICAL_PADDING,
                            bottom = Dimensions.FRIENDS_FIELD_VERTICAL_PADDING
                        )
                ) {
                    items(data.value.listOfFriends) { listOfFriends ->
                        Spacer(modifier = Modifier.height(Dimensions.SPACER_FROM_FRIEND_UP))
                        FriendItemWithSwipe(
                            friend = listOfFriends,
                            modifier = Modifier,
                            onRemove = { viewModel.removeFriend(it.users.id) })
                        Spacer(modifier = Modifier.height(Dimensions.SPACER_FROM_FRIEND_DOWN))
                    }
                }
            }
        }
        FloatingPlusButton(modifier = Modifier
            .align(Alignment.BottomEnd)
            .semantics {
                contentDescription = Constants.FRIENDS_ADD_BUTTON
            }) {}
    }

    LaunchedEffect(key1 = true) {
        viewModel.clearSearchInput()
    }
}