package com.example.hangoutz.ui.loginscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hangoutz.ui.navigation.NavigationItem

@Composable
fun MyEventsScreen(navController: NavController) {
    Text(text = "My events")
    Column(modifier = Modifier.padding(top = 90.dp, end = 30.dp)) {
        Button(onClick = {
            navController.navigate(NavigationItem.MainScreen.route)
        }) {
            Text(text = "Go to Main")
        }
        Button(onClick = {
            navController.navigate(NavigationItem.Invited.route)
        }) {
            Text(text = "Go to Invited")
        }
        Button(onClick = {
            navController.navigate(NavigationItem.EventDetailsOwner.route)
        }) {
            Text(text = "Go to EventDetailsOwner")
        }
        Button(onClick = {
            navController.navigate(NavigationItem.CreateEvent.route)
        }) {
            Text(text = "Go to CreateEvent")
        }
        Button(onClick = {
            navController.navigate(NavigationItem.Friends.route)
        }) {
            Text(text = "Go to Friends")
        }
        Button(onClick = {
            navController.navigate(NavigationItem.Settings.route)
        }) {
            Text(text = "Go to Settings")
        }
    }
}



