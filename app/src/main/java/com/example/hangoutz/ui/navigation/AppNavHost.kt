package com.example.hangoutz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hangoutz.ui.screens.createeventscreen.CreateEventScreen
import com.example.hangoutz.ui.screens.eventdetailsscreen.EventDetailsScreen
import com.example.hangoutz.ui.screens.eventdetailsownerscreen.EventOwnerDetailsScreen
import com.example.hangoutz.ui.screens.friendsscreen.FriendsScreen
import com.example.hangoutz.ui.screens.invitedscreen.InvitedScreen
import com.example.hangoutz.ui.screens.loginscreen.LoginScreen
import com.example.hangoutz.ui.screens.mainscreen.MainScreen
import com.example.hangoutz.ui.screens.myeventsscreen.MyEventsScreen
import com.example.hangoutz.ui.screens.registerscreen.RegisterScreen
import com.example.hangoutz.ui.screens.settingsscreen.SettingsScreen
import com.example.hangoutz.ui.screens.splash.SplashScreen

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
            SplashScreen()
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

