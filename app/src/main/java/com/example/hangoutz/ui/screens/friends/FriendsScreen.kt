package com.example.hangoutz.ui.screens.friends

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.hangoutz.ui.components.FloatingPlusButton
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FriendsScreen(viewModel: FriendsViewModel = hiltViewModel()) {
    val data = viewModel.uiState.collectAsState()
    Box(
        modifier = Modifier
            .padding(Dimensions.FRIENDS_OUTER_PADDING)
            .semantics {
                contentDescription = Constants.FRIENDS_BACKGROUND_BOX
            }
    ) {
        // MBLINTER-7: Search bar
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
        FloatingPlusButton(modifier = Modifier
            .align(Alignment.BottomEnd)
            .semantics {
                contentDescription = Constants.FRIENDS_ADD_BUTTON
            }) {}
    }
    LaunchedEffect(key1 = true) {
        viewModel.initUiState()
    }
}