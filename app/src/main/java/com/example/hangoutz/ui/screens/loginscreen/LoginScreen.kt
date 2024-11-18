package com.example.hangoutz.ui.screens.loginscreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hangoutz.ui.screens.navigation.NavigationItem

@Composable
fun LoginScreen(navController: NavController) {
    Text(text = "Login page")
    Row(
        modifier = Modifier.padding(top = 90.dp, end = 30.dp)
    ) {
        Button(onClick = {
            navController.navigate(NavigationItem.Register.route)
        }) {
            Text(text = "Go to Register")
        }
        Button(onClick = {
            navController.navigate(NavigationItem.MainScreen.route)
        }) {
            Text(text = "Go to Main")
        }
    }
}