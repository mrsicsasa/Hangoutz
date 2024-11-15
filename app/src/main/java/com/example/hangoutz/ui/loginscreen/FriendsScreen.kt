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
fun FriendsScreen (navController: NavController) {
    Text(
        text="Friends List"
    )

    Column (modifier = Modifier.padding(top = 30.dp, end = 30.dp)){

    Button(onClick = {
        navController.navigate(NavigationItem.Settings.route)
    }) {
        Text(text = "Go to Settings")
    }

        Button(onClick = {
            navController.navigate(NavigationItem.MainScreen.route)
        }) {
            Text(text = "Go to Main")
        }
    Button(

        onClick = { navController.popBackStack() }) {
        Text("Back")
    }}
}



