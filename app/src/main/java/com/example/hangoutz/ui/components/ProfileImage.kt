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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hangoutz.BuildConfig
import com.example.hangoutz.R
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.utils.Dimensions

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileScreen(
    imageUrl: String,
    boxModifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier
) {
    Box(
        modifier = boxModifier
            .size(Dimensions.PROFILE_IMAGE_SIZE)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl.isNotEmpty()) {
            GlideImage(
                model = "${BuildConfig.BASE_URL_AVATAR}${imageUrl}",
                contentDescription = stringResource(R.string.user_profile_image),
                contentScale = ContentScale.Crop,
                modifier = imageModifier
                    .size(Dimensions.PROFILE_IMAGE_SIZE)
                    .clip(CircleShape)
                    .border(Dimensions.PROFILE_IMAGE_BORDER_WIDTH, Ivory, CircleShape)
            )
        }
    }
}