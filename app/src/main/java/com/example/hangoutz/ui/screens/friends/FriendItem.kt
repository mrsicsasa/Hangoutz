package com.example.hangoutz.ui.screens.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hangoutz.BuildConfig
import com.example.hangoutz.ui.theme.Charcoal
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.ui.theme.Orange
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FriendItem(
    avatar: String?,
    name: String
) {
    Box(
        Modifier.padding(

        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
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
                    model = "${BuildConfig.BASE_URL_AVATAR}${avatar ?: Constants.DEFAULT_USER_PHOTO}",
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
                text = name,
                style = MaterialTheme.typography.titleMedium.copy(color = Charcoal),
                modifier = Modifier
                    .padding(start = Dimensions.FRIENDS_TEXT_START_PADDING)
                    .semantics { contentDescription = Constants.FRIENDS_LIST_NAME }
            )
        }
    }
}