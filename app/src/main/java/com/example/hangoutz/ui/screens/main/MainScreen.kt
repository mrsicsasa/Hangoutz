package com.example.hangoutz.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hangoutz.ui.navigation.BottomNavItem
import com.example.hangoutz.ui.navigation.BottomNavigationDestination
import com.example.hangoutz.ui.screens.friendsscreen.FriendsScreen
import com.example.hangoutz.ui.screens.myeventsscreen.MyEventsScreen
import com.example.hangoutz.ui.screens.settingsscreen.SettingsScreen
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.ui.theme.bottomNavigationColor
import com.example.hangoutz.ui.theme.topBarBackgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    var currentScreen = remember { mutableStateOf(BottomNavigationDestination.EVENTS) }
    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier
                .height(55.dp)
                .wrapContentHeight(align = Alignment.CenterVertically),
            title = {
                Text(
                    text = "Hangoutz",
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
                ), currentScreen
            )
        }

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            when (currentScreen.value) {
                BottomNavigationDestination.EVENTS -> MyEventsScreen(navController)
                BottomNavigationDestination.FRIENDS -> FriendsScreen(navController)
                BottomNavigationDestination.SETTINGS -> SettingsScreen(navController)
            }
        }
    }
}

@Composable
fun TabView(
    tabBarItems: List<BottomNavItem>,
    currentScreen: MutableState<BottomNavigationDestination>
) {

    NavigationBar(
        modifier = Modifier.height(67.dp),
        containerColor = bottomNavigationColor,

        ) {
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                modifier = Modifier.padding(top = 18.dp),
                selected = tabBarItem.route == currentScreen.value,
                onClick = {
                    currentScreen.value = tabBarItem.route
                },
                icon = {
                    Image(painterResource(tabBarItem.icon), contentDescription = "")
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = Ivory,
                    selectedTextColor = Ivory,
                    selectedIndicatorColor = Ivory,
                    unselectedIconColor = bottomNavigationColor,
                    unselectedTextColor = Ivory,
                    disabledIconColor = Color.Blue,
                    disabledTextColor = Color.Blue
                )
            )
        }
    }
}