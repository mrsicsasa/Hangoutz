package com.example.hangoutz.ui.screens.eventDetails

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.ui.theme.Orange
import com.example.hangoutz.utils.Constants.PROFILE_PHOTO
import com.example.hangoutz.utils.Constants.SETTINGS_USER_PHOTO_TAG

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ParticipantUI(name: String, userAvatar: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            GlideImage(
                model = userAvatar,
                contentDescription = PROFILE_PHOTO,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically)
                    .testTag(SETTINGS_USER_PHOTO_TAG)
                    .border(2.dp, Orange, CircleShape)
            )


            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                modifier = Modifier.padding(start = 12.dp)
            )
        }
        HorizontalDivider(
            color = Ivory,
            thickness = 2.dp,
            modifier = Modifier.padding(top = 15.dp)
        )
    }
}