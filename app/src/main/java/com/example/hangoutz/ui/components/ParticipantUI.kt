package com.example.hangoutz.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hangoutz.BuildConfig
import com.example.hangoutz.R
import com.example.hangoutz.data.models.User
import com.example.hangoutz.ui.theme.DeleteColor
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.ui.theme.Orange
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Constants.DEFAULT_USER_PHOTO
import com.example.hangoutz.utils.Constants.PROFILE_PHOTO
import com.example.hangoutz.utils.Constants.SETTINGS_USER_PHOTO_TAG
import com.example.hangoutz.utils.Dimensions

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ParticipantUI(participant: User, isOwner : Boolean = false, onClick: () -> Unit) {
    val displayAvatar = if (participant.avatar?.isNotBlank() == true) participant.avatar else DEFAULT_USER_PHOTO
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimensions.CREATE_EVENT_PARTICIPANT)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            GlideImage(
                model = "${BuildConfig.BASE_URL_AVATAR}${displayAvatar}",
                contentDescription = PROFILE_PHOTO,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(Dimensions.CREATE_EVENT_PARTICIPANT_PHOTO)
                    .height(Dimensions.CREATE_EVENT_PARTICIPANT_PHOTO)
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically)
                    .testTag(SETTINGS_USER_PHOTO_TAG)
                    .border(Dimensions.CREATE_EVENT_LINE_THICKNESS, Orange, CircleShape)
                    .semantics {
                        contentDescription = Constants.PARTICIPANT_IMAGE
                    }
            )

            Text(
                text = participant.name,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                modifier = Modifier.padding(start = Dimensions.CREATE_EVENT_TEXT_PADDING2)
            )
            if(isOwner){
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(end = Dimensions.CREATE_EVENT_ICON_PADDING),
                horizontalArrangement = Arrangement.End

            ) {

                Icon(
                    Icons.Outlined.Clear,
                    contentDescription = "Image",
                    tint = DeleteColor,
                    modifier = Modifier.clickable { onClick() }
                        .semantics {
                            contentDescription = Constants.PARTICIPANT_ICON_TAG
                        },
                )
            }
        }
        }
        HorizontalDivider(
            color = Orange,
            thickness = Dimensions.CREATE_EVENT_LINE_THICKNESS,
            modifier = Modifier.padding(top = Dimensions.CREATE_EVENT_TEXT_PADDING)
                .semantics {
                    contentDescription = Constants.PARTICIPANT_DIVIDER
                },
        )
    }
}