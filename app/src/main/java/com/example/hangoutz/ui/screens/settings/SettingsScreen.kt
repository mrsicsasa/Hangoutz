package com.example.hangoutz.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(navController: NavController) {

    Text(text = "Settings")
    Column(modifier = Modifier.padding(top = 90.dp, end = 30.dp)) {
    }
}