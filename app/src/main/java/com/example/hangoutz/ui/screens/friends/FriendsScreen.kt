package com.example.hangoutz.ui.screens.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hangoutz.BuildConfig
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.FloatingPlusButton
import com.example.hangoutz.ui.components.SearchField
import com.example.hangoutz.ui.theme.Charcoal
import com.example.hangoutz.ui.theme.CoolGray
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.ui.theme.Orange
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
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
                    viewModel.loadFriends(true)
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
                                        .border(
                                            Dimensions.FRIENDS_AVATAR_BORDER,
                                            Orange,
                                            CircleShape
                                        )
                                ) {
                                    // Avatar
                                    GlideImage(
                                        model = "${BuildConfig.BASE_URL_AVATAR}${listOfFriends.users.avatar ?: Constants.DEFAULT_USER_PHOTO}",
                                        contentDescription = Constants.FRIENDS_PROFILE_PICTURE_DESCRIPTION,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(Dimensions.FRIENDS_AVATAR_SIZE)
                                            .clip(CircleShape)
                                            .semantics {
                                                contentDescription =
                                                    Constants.FRIENDS_LIST_PHOTO
                                            }
                                    )
                                }
                                // Name
                                Text(
                                    text = listOfFriends.users.name,
                                    style = MaterialTheme.typography.titleMedium.copy(color = Charcoal),
                                    modifier = Modifier
                                        .padding(start = Dimensions.FRIENDS_TEXT_START_PADDING)
                                        .semantics {
                                            contentDescription = Constants.FRIENDS_LIST_NAME
                                        }
                                )
                            }
                        }
                    }
                    viewModel.loadFriends(false)
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