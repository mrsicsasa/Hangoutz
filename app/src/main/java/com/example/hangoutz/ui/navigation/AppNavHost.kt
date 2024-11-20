package com.example.hangoutz.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hangoutz.ui.screens.createeventscreen.CreateEventScreen
import com.example.hangoutz.ui.screens.eventdetailsownerscreen.EventOwnerDetailsScreen
import com.example.hangoutz.ui.screens.eventdetailsscreen.EventDetailsScreen
import com.example.hangoutz.ui.screens.invitedscreen.InvitedScreen
import com.example.hangoutz.ui.screens.loginscreen.LoginScreen
import com.example.hangoutz.ui.screens.loginscreen.LoginViewModel
import com.example.hangoutz.ui.screens.mainscreen.MainScreen
import com.example.hangoutz.ui.screens.myeventsscreen.MyEventsScreen
import com.example.hangoutz.ui.screens.main.MainScreen
import com.example.hangoutz.ui.screens.registerscreen.RegisterScreen
import com.example.hangoutz.ui.screens.splash.SplashScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Splash.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Splash.route){
            SplashScreen(navController)
        }
        composable(NavigationItem.Login.route) {
            LoginScreen(navController)
        }
        composable(NavigationItem.Register.route) {
            RegisterScreen(navController)
        }
        composable(NavigationItem.MainScreen.route) {
            MainScreen(navController)
        }
        composable(NavigationItem.Invited.route) {
            InvitedScreen(navController)
        }
        composable(NavigationItem.CreateEvent.route) {
            CreateEventScreen(navController)
        }
        composable(NavigationItem.EventDetails.route) {
            EventDetailsScreen(navController)
        }
        composable(NavigationItem.EventDetailsOwner.route) {
            EventOwnerDetailsScreen(navController)
        }
    }
}

