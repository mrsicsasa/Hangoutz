package com.example.hangoutz.ui.components

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import com.example.hangoutz.R
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions
import kotlinx.coroutines.delay

@Composable
fun Logo(
    painter: Painter,
    initialValue: Float = 1f,
    targetValue: Float = 1f,
    animationDelay: Long,
    modifier: Modifier
) {
    val scale = remember { Animatable(initialValue) }
    Image(
        painter = painter,
        contentDescription = stringResource(R.string.application_logo),
        modifier = modifier
            .height(Dimensions.LOGO_HEIGHT)
            .scale(scale.value)
    )
    LaunchedEffect(key1 = true) {
        delay(animationDelay)
        scale.animateTo(
            targetValue = targetValue,
            animationSpec = tween(
                durationMillis = Constants.LOGO_ANIMATION_DURATION,
                easing = {
                    OvershootInterpolator(Dimensions.LOGO_OVERSHOOT_INTERPOLATOR_TENSION).getInterpolation(
                        it
                    )
                })
        )
    }
}
