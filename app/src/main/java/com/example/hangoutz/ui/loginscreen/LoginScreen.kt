package com.example.hangoutz.ui.loginscreen

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.hangoutz.ui.theme.inter

@Composable
fun LoginScreen (navController1: NavController) {
SwitchScreen(
    onClickSwitchToRegister = {
        navController1.navigate("register")
    }
)
}

@Composable
fun SwitchScreen(
    onClickSwitchToRegister: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Text(
        text = "Go to Register",

        fontFamily = inter,
        modifier = modifier
            .clickable {
                onClickSwitchToRegister();
            }

    )
}


