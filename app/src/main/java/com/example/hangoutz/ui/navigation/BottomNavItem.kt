package com.example.hangoutz.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.hangoutz.R

sealed class BottomNavItem(val route: BottomNavigationDestination, val icon: Int) {
    object Events: BottomNavItem(route = BottomNavigationDestination.EVENTS, icon = R.drawable.ic_events_default)
    object Friends: BottomNavItem(route = BottomNavigationDestination.FRIENDS, icon = R.drawable.ic_friends_default)
    object Settings: BottomNavItem(route = BottomNavigationDestination.SETTINGS, icon = R.drawable.ic_settings_default)
}
enum class BottomNavigationDestination {
    EVENTS,
    FRIENDS,
    SETTINGS,
}
