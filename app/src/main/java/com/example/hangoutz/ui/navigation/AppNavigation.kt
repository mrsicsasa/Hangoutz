package com.example.hangoutz.ui.navigation

enum class Screen {
    LOGIN,
    REGISTER,
    MAIN,
    EVENTS,
    CREATE_EVENT,
    EVENT_DETAILS,
    EVENT_OWNER,
    SPLASH
}

sealed class NavigationItem(val route: String) {
    object Register : NavigationItem(Screen.REGISTER.name)
    object Login : NavigationItem(Screen.LOGIN.name)
    object MainScreen : NavigationItem(Screen.MAIN.name)
    object Events : NavigationItem(Screen.EVENTS.name)
    object CreateEvent : NavigationItem(Screen.CREATE_EVENT.name)
    object EventDetails : NavigationItem(Screen.EVENT_DETAILS.name + "/{eventId}")
    object EventDetailsOwner : NavigationItem(Screen.EVENT_OWNER.name + "/{eventId}")
    object Splash : NavigationItem(Screen.SPLASH.name)
}
