package com.example.hangoutz.utils

object Constants {
    //SharedPreferences
    val PREF_NAME = "hangoutz-shared-preferences"
    val KEY_USER_ID = "user_id"

    //Splash screen
    val LOGO_ANIMATION_DELAY_SPLASH : Long = 800
    val LOGO_ANIMATION_DURATION : Int = 500
    val BACKGROUND_ANIMATION_DURATION: Int = 2000
    val LOGO_ANIMATION_DELAY : Long = 100

    //credentials
    val EMAIL = "Email"
    val PASSWORD = "Password"
    val LOGIN = "Login"
    val LOGOUT = "Logout"
    val NAME = "Name"

    //Top bar
    val TOP_BAR_TITLE = "Hangoutz"

    //Errors
    val ERROR_EMPTY_FIELDS = "All fields must be filled!"
    val ERROR_INVALID_INPUT = "Incorrect email or password"

    //Default user photo
    val DEFAULT_USER_PHOTO = "avatar_default.png"
}