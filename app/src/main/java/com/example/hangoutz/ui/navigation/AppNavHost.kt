package com.example.hangoutz.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hangoutz.ui.screens.createEvent.CreateEventScreen
import com.example.hangoutz.ui.screens.eventDetailsOwner.EventOwnerDetailsScreen
import com.example.hangoutz.ui.screens.eventDetails.EventDetailsScreen
import com.example.hangoutz.ui.screens.invite.InvitedScreen
import com.example.hangoutz.ui.screens.login.LoginScreen
import com.example.hangoutz.ui.screens.main.MainScreen
import com.example.hangoutz.ui.screens.register.RegisterScreen
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
                CreateEventScreen()
        }
        composable(NavigationItem.EventDetailsOwner.route) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            if (eventId != null) {
                EventOwnerDetailsScreen(navController)
            }
        }
        composable(NavigationItem.EventDetails.route) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            if (eventId != null) {
                EventDetailsScreen(navController)
            }
        }
        }
    }


