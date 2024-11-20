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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hangoutz.R
import com.example.hangoutz.ui.navigation.BottomNavItem
import com.example.hangoutz.ui.navigation.BottomNavigationDestination
import com.example.hangoutz.ui.screens.friendsscreen.FriendsScreen
import com.example.hangoutz.ui.screens.myeventsscreen.MyEventsScreen
import com.example.hangoutz.ui.screens.settingsscreen.SettingsScreen
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.ui.theme.topBarBackgroundColor
import com.example.hangoutz.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val bottomNavController = rememberNavController()
    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier
                .height(55.dp)
                .wrapContentHeight(align = Alignment.CenterVertically),
            title = {
                Text(
                    text = Constants.TOP_BAR_TITLE,
                    color = Ivory,
                    style = MaterialTheme.typography.titleLarge,
                )

            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = topBarBackgroundColor
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
                FriendsScreen(navController)
            }
            composable(route = BottomNavigationDestination.SETTINGS.name) {
                SettingsScreen(navController)
            }
        }
    }
}

