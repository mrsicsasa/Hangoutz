package com.example.hangoutz.ui.screens.splash

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.Logo

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SplashScreen() {
    val viewmodel: SplashScreenViewModel = viewModel()
    val alpha = remember { Animatable(0.4f) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.main_background),
                contentScale = ContentScale.FillBounds,
                alpha = alpha.value

            ),
        contentAlignment = Alignment.Center
    ) {
        Logo(
            painterResource(id = R.drawable.logo),
            initialValue = 1f,
            targetValue = 0f,
            modifier = Modifier.align(Alignment.Center)
        )

    }
    LaunchedEffect(key1 = true) {
        alpha.animateTo(1f, animationSpec = tween(2000))
        viewmodel.deleteEventsFromPast()

    }
}
