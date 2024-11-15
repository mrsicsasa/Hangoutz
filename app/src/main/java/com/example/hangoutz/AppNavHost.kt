package com.example.hangoutz

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hangoutz.ui.loginscreen.LoginScreen
import com.example.hangoutz.ui.loginscreen.RegisterScreen


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Login.route
    ) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        composable(NavigationItem.Login.route) {
            LoginScreen(navController)
        }


        composable(NavigationItem.Register.route) {
            RegisterScreen(navController)
        }
    }
}

class AppNavHost {


}