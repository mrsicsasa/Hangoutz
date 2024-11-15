package com.example.hangoutz.ui.loginscreen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.hangoutz.NavigationItem

@Composable
fun RegisterScreen (navController: NavController) {
    Button(onClick = {

        navController.navigate(NavigationItem.Login.route)
    }) {
        Text(text = "Go to Login")
    }
}



