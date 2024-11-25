package com.example.hangoutz.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hangoutz.BuildConfig
import com.example.hangoutz.R
import com.example.hangoutz.ui.theme.Ivory

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileScreen(
    imageUrl: String,
    boxModifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier
) {
    Box(
        modifier = boxModifier
            .size(74.dp)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl.isNotEmpty()) {
            GlideImage(
                model = "${BuildConfig.BASE_URL_AVATAR}${imageUrl}",
                contentDescription = stringResource(R.string.user_profile_image),
                contentScale = ContentScale.Crop,
                modifier = imageModifier
                    .size(74.dp)
                    .clip(CircleShape)
                    .border(2.dp, Ivory, CircleShape)
            )
        }
    }
}