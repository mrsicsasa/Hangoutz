package com.example.hangoutz

enum class Screen{
    LOGIN,
    REGISTER
}

sealed class NavigationItem(val route: String) {
    object Register : NavigationItem(Screen.REGISTER.name)
    object Login : NavigationItem(Screen.LOGIN.name)
}

class AppNavigation {

}