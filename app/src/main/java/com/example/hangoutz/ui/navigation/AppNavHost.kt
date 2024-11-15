package com.example.hangoutz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hangoutz.ui.loginscreen.CreateEventScreen
import com.example.hangoutz.ui.loginscreen.EventDetailsScreen
import com.example.hangoutz.ui.loginscreen.EventOwnerDetailsScreen
import com.example.hangoutz.ui.loginscreen.FriendsScreen
import com.example.hangoutz.ui.loginscreen.InvitedScreen
import com.example.hangoutz.ui.loginscreen.LoginScreen
import com.example.hangoutz.ui.loginscreen.MainScreen
import com.example.hangoutz.ui.loginscreen.MyEventsScreen
import com.example.hangoutz.ui.loginscreen.RegisterScreen
import com.example.hangoutz.ui.loginscreen.SettingsScreen

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
        composable(NavigationItem.MainScreen.route) {
            MainScreen(navController)
        }
        composable(NavigationItem.Invited.route) {
            InvitedScreen(navController)
        }
        composable(NavigationItem.MyEvents.route) {
            MyEventsScreen(navController)
        }
        composable(NavigationItem.CreateEvent.route) {
            CreateEventScreen(navController)
        }
        composable(NavigationItem.Friends.route) {
            FriendsScreen(navController)
        }
        composable(NavigationItem.Settings.route) {
            SettingsScreen(navController)
        }
        composable(NavigationItem.EventDetails.route) {
            EventDetailsScreen(navController)
        }
        composable(NavigationItem.EventDetailsOwner.route) {
            EventOwnerDetailsScreen(navController)
        }
    }
}

