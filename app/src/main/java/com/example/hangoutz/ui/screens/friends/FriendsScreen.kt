package com.example.hangoutz.ui.screens.friends

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hangoutz.BuildConfig
import com.example.hangoutz.data.models.Friend
import com.example.hangoutz.utils.Constants

@Composable
fun FriendsScreen(navController: NavController, viewModel: FriendsViewModel = hiltViewModel()) {
    val data = viewModel.uiState.collectAsState()
    // MBLINTER-7: Search bar
    LazyColumn {
        items(data.value.friendWrappers) { friendRoot ->
            FriendItem(friendRoot.users)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FriendItem(friend: Friend, modifier: Modifier = Modifier) {
    // Avatar
    GlideImage(
        model = "${BuildConfig.BASE_URL_AVATAR}${friend.avatar}",
        contentDescription = Constants.FRIENDS_PROFILE_PICTURE_DESCRIPTION,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .width(50.dp)
            .height(50.dp)
            .clip(CircleShape)
            .semantics { contentDescription = Constants.FRIENDS_LIST_PHOTO }
    )
    // Name
    Text(
        text = friend.name,
        modifier = modifier.semantics { contentDescription = Constants.FRIENDS_LIST_NAME }
    )
}