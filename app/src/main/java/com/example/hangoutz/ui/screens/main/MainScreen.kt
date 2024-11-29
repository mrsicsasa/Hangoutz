package com.example.hangoutz.ui.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hangoutz.R
import com.example.hangoutz.ui.navigation.BottomNavItem
import com.example.hangoutz.ui.navigation.BottomNavigationDestination
import com.example.hangoutz.ui.navigation.NavigationItem
import com.example.hangoutz.ui.screens.eventDetails.EventDetailsScreen
import com.example.hangoutz.ui.screens.eventDetailsOwner.EventOwnerDetailsScreen
import com.example.hangoutz.ui.screens.events.MyEventsScreen
import com.example.hangoutz.ui.screens.friends.FriendsScreen
import com.example.hangoutz.ui.screens.login.LoginScreen
import com.example.hangoutz.ui.screens.settings.SettingsScreen
import com.example.hangoutz.ui.theme.TopBarBackgroundColor
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val bottomNavController = rememberNavController()
    val eventDetailsNavController = rememberNavController()
    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier
                .height(Dimensions.TOP_BAR_HEIGHT)
                .wrapContentHeight(align = Alignment.CenterVertically)
                .semantics {
                    contentDescription = Constants.TOP_BAR
                },
            title = {
                Text(
                    text = Constants.TOP_BAR_TITLE,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.semantics {
                        contentDescription = Constants.TOP_BAR_TITLE
                    }
                )

            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = TopBarBackgroundColor
            )
        )
    },

        bottomBar = {
            TabView(
                tabBarItems = listOf(
                    BottomNavItem.Events,
                    BottomNavItem.Friends,
                    BottomNavItem.Settings
                ),
                navController = bottomNavController
            )
        }


    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavigationDestination.EVENTS.name,
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.main_background),
                    contentScale = ContentScale.FillBounds
                )
                .padding(innerPadding)
        ) {
            composable(route = BottomNavigationDestination.EVENTS.name) {
                MyEventsScreen(navController)
            }
            composable(route = BottomNavigationDestination.FRIENDS.name) {
                FriendsScreen()
            }
            composable(route = BottomNavigationDestination.SETTINGS.name) {
                SettingsScreen(navController)
            }
        }
}}

