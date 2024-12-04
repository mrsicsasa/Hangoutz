package com.example.hangoutz.ui.screens.noInternet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hangoutz.R
import com.example.hangoutz.utils.Constants

@Composable
fun NoInternetScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.no_internet_background),
                contentScale = ContentScale.FillBounds,
            )
            .semantics {
                contentDescription = Constants.SPLASH_SCREEN_BACKGROUND
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .padding(top = 30.dp)
              // .background(Color.Red),
            ,verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                "No internet connection",
                style = MaterialTheme.typography.titleLarge.copy(Color.DarkGray)
            )
        }
    }
}