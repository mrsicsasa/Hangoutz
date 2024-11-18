package com.example.hangoutz.ui.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.Logo

@Composable
fun SplashScreen() {
    Box(
        modifier = with(Modifier) {
            fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.main_background),
                    contentScale = ContentScale.FillBounds
                )
        }
    ){
        Logo(painterResource(id = R.drawable.logo))
    }
}
@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}