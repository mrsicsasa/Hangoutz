package com.example.hangoutz.ui.loginscreen

import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.hangoutz.NavigationItem
import com.example.hangoutz.ui.theme.inter

@Composable
fun LoginScreen (navController: NavController) {
    Button(onClick = {

        navController.navigate(NavigationItem.Register.route)
    }) {
        Text(text = "Go to Register")
    }
}



