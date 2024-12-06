package com.example.hangoutz.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hangoutz.BuildConfig
import com.example.hangoutz.R
import com.example.hangoutz.ui.theme.DeleteColor
import com.example.hangoutz.ui.theme.Orange
import com.example.hangoutz.ui.theme.OrangeButton
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Constants.PROFILE_PHOTO
import com.example.hangoutz.utils.Dimensions

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DisplayUser(
    name: String,
    userAvatar: String?,
    isCheckList: Boolean = false,
    isParticipant: Boolean = false,
    isCheckedInitial: Boolean = false,
    onChange: (Boolean) -> Unit = {},
    addFriend: () -> Unit = {},
    onRemove: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimensions.BOTTOM_SHEET_USER_PADDING)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                GlideImage(
                    model = "${BuildConfig.BASE_URL_AVATAR}${userAvatar ?: Constants.DEFAULT_USER_PHOTO}",
                    contentDescription = PROFILE_PHOTO,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(Dimensions.FLOATING_ICON_SIZE)
                        .clip(CircleShape)
                        .align(Alignment.CenterVertically)
                        .border(Dimensions.FLOATING_BUTTON_BORDER, Orange, CircleShape)
                        .semantics {
                            contentDescription = Constants.BOTTOM_SHEET_PROFILE_PICTURE
                        }
                )
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium.copy(color = if (isParticipant) Color.White else Color.Black),
                    modifier = Modifier
                        .padding(start = Dimensions.BOTTOM_SHEET_NAME_PADING)
                        .semantics {
                            contentDescription = Constants.BOTTOM_SHEET_USERNAME
                        }
                )
            }
            Box(
                contentAlignment = Alignment.CenterVertically,
                modifier = Modifier.background(Color.Green)
            ) {
                if (isCheckList) {
                    Checkbox(
                        checked = isCheckedInitial,
                        onCheckedChange = { onChange(!isCheckedInitial) }
                    )

                } else if (isParticipant) {
                    IconButton(
                        onClick = {
                            onRemove()
                        }
                    ) {
                        Icon(
                            Icons.Outlined.Clear,
                            contentDescription = stringResource(R.string.remove_participant_icon),
                            tint = DeleteColor,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(Dimensions.ADD_ICON_SIZE)
                        )
                    }

                } else {
                    IconButton(
                        onClick = {
                            addFriend()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(R.string.add_friend_button),
                            tint = Color.White,
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Orange)
                                .size(Dimensions.ADD_ICON_SIZE)
                                .semantics {
                                    contentDescription = Constants.BOTTOM_SHEET_ADD_ICON
                                }
                        )
                    }
                }
            }
        }
        HorizontalDivider(
            color = if (isParticipant) OrangeButton else Color.Black,
            thickness = Dimensions.BOTTOM_SHEET_DIVIDER_WIDTH
        )
    }
}

