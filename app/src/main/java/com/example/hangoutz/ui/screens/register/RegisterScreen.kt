package com.example.hangoutz.ui.screens.register

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
fun RegisterScreen(navController: NavController) {
    Text(text = "Registration page")
    Column(
        modifier = Modifier.padding(top = 90.dp)
    ) {
        Button(
            onClick = {
                navController.navigate(NavigationItem.Login.route)
            }) {
            Text(text = "Go to Login")
        }
        Button(
            onClick = { navController.popBackStack() }) {
            Text(text = "Back")
        }
    }
}