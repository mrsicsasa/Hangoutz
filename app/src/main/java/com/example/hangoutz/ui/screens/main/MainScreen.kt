package com.example.hangoutz.ui.screens.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.hangoutz.ui.theme.topBarBackgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    Scaffold (topBar = {
        TopAppBar(
        title = {
            Text(
                text = "Hangoutz",
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = topBarBackgroundColor
        )
    )
    }

    ){ it }
}