package com.example.hangoutz.ui.screens.navigation

enum class Screen {
    LOGIN,
    REGISTER,
    MAIN,
    INVITED,
    MY_EVENTS,
    CREATE_EVENT,
    FRIENDS,
    SETTINGS,
    EVENT_DETAILS,
    EVENT_OWNER
}

sealed class NavigationItem(val route: String) {
    object Register : NavigationItem(Screen.REGISTER.name)
    object Login : NavigationItem(Screen.LOGIN.name)
    object MainScreen : NavigationItem(Screen.MAIN.name)
    object Invited : NavigationItem(Screen.INVITED.name)
    object MyEvents : NavigationItem(Screen.MY_EVENTS.name)
    object CreateEvent : NavigationItem(Screen.CREATE_EVENT.name)
    object Friends : NavigationItem(Screen.FRIENDS.name)
    object Settings : NavigationItem(Screen.SETTINGS.name)
    object EventDetails : NavigationItem(Screen.EVENT_DETAILS.name)
    object EventDetailsOwner : NavigationItem(Screen.EVENT_OWNER.name)
}

class AppNavigation {

}