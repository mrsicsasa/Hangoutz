package com.example.hangoutz.ui.screens.eventDetails

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun EventDetailsScreen(navController: NavController) {
    Text(text = "Event details")
    Row(modifier = Modifier.padding(top = 30.dp, end = 30.dp)) {
        Button(
            onClick = { navController.popBackStack() }) {
            Text(text = "Back")
        }
    }
}