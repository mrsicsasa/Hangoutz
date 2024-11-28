package com.example.hangoutz.ui.screens.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hangoutz.BuildConfig
import com.example.hangoutz.ui.components.FloatingPlusButton
import com.example.hangoutz.ui.theme.Charcoal
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.ui.theme.Orange
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
                Box(
                    Modifier.padding(
                        top = Dimensions.FRIENDS_OUTER_PADDING,
                        bottom = Dimensions.FRIENDS_OUTER_PADDING
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(Dimensions.FRIENDS_FIELD_ROUNDED_CORNER))
                            .background(Ivory)
                            .padding(
                                top = Dimensions.FRIENDS_INNER_VERTICAL_PADDING,
                                bottom = Dimensions.FRIENDS_INNER_VERTICAL_PADDING,
                                start = Dimensions.FRIENDS_HORIZONTAL_PADDING
                            )
                            .semantics {
                                contentDescription = Constants.FRIENDS_LIST_ELEMENT
                            }
                    ) {
                        // Image border
                        Box(
                            modifier = Modifier
                                .size(Dimensions.FRIENDS_AVATAR_SIZE)
                                .clip(CircleShape)
                                .border(Dimensions.FRIENDS_AVATAR_BORDER, Orange, CircleShape)
                        ) {
                            // Avatar
                            GlideImage(
                                model = "${BuildConfig.BASE_URL_AVATAR}${listOfFriends.users.avatar ?: Constants.DEFAULT_USER_PHOTO}",
                                contentDescription = Constants.FRIENDS_PROFILE_PICTURE_DESCRIPTION,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(Dimensions.FRIENDS_AVATAR_SIZE)
                                    .clip(CircleShape)
                                    .semantics { contentDescription = Constants.FRIENDS_LIST_PHOTO }
                            )
                        }
                        // Name
                        Text(
                            text = listOfFriends.users.name,
                            style = MaterialTheme.typography.titleMedium.copy(color = Charcoal),
                            modifier = Modifier
                                .padding(start = Dimensions.FRIENDS_TEXT_START_PADDING)
                                .semantics { contentDescription = Constants.FRIENDS_LIST_NAME }
                        )
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
}