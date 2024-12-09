package com.example.hangoutz.ui.navigation

import com.example.hangoutz.R

sealed class BottomNavItem(
    val route: BottomNavigationDestination,
    val icon: Int,
    val selectedIcon: Int
) {
    data object Events : BottomNavItem(
        route = BottomNavigationDestination.EVENTS,
        icon = R.drawable.ic_events_default,
        selectedIcon = R.drawable.ic_events_selected
    )

    data object Friends : BottomNavItem(
        route = BottomNavigationDestination.FRIENDS,
        icon = R.drawable.ic_friends_default,
        selectedIcon = R.drawable.ic_friends_selected
    )

    data object Settings : BottomNavItem(
        route = BottomNavigationDestination.SETTINGS,
        icon = R.drawable.ic_settings_default,
        selectedIcon = R.drawable.ic_settings_selected
    )
}

enum class BottomNavigationDestination {
    EVENTS,
    FRIENDS,
    SETTINGS,
}
