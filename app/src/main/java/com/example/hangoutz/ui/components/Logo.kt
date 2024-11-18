package com.example.hangoutz.ui.components

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.hangoutz.R
import kotlinx.coroutines.delay

@Composable
fun Logo(
    painter: Painter,
    initialValue: Float = 1f,
    targetValue: Float = 1f,
    modifier: Modifier
) {
    val scale = remember { Animatable(initialValue) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Image(
            painter = painter,
            contentDescription = stringResource(R.string.application_logo),
            modifier = modifier
                .height(50.dp)
                .scale(scale.value)
        )
        LaunchedEffect(key1 = true) {
            delay(800)
            scale.animateTo(
                targetValue = targetValue,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = { OvershootInterpolator(0f).getInterpolation(it) })
            )
        }
    }
}