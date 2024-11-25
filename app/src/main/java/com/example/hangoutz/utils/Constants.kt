package com.example.hangoutz.utils

object Constants {
    //SharedPreferences
    const val PREF_NAME = "hangoutz-shared-preferences"
    const val KEY_USER_ID = "user_id"

    //Splash screen
    const val LOGO_ANIMATION_DELAY_SPLASH : Long = 800
    const val LOGO_ANIMATION_DURATION : Int = 500
    const val BACKGROUND_ANIMATION_DURATION: Int = 2000
    const val SPLASH_SCREEN_BACKGROUND = "SplashScreenBackground"
    const val SPLASH_SCREEN_LOGO = "SplashScreenLogo"
    const val LOGO_ANIMATION_DELAY: Long = 500

    //credentials
    const val EMAIL = "Email"
    const val PASSWORD = "Password"
    const val LOGIN = "Login"

    //Top bar
    const val TOP_BAR_TITLE = "Hangoutz"

    //Error codes
    const val DUPLICATE_ITEM = 409

    //Errors
    const val ERROR_EMPTY_FIELDS = "All fields must be filled!"
    const val ERROR_INVALID_INPUT = "Incorrect email or password"
    //Events screen
    const val GET_EVENTS_ERRORS = "Loading events errors"
    const val CREATE_EVENT_BUTTON = "CreateEventButton"

    //EventCard
    const val EVENT_CARD = "EventCard"
    const val LOGO_BACKGROUND = "LogoBackground"
    const val LOGO_IMAGE = "LogoImage"
    const val DECLINE_INVITATION_BUTTON = "DeclineInvitationButton"
    const val ACCEPT_INVITATION_BUTTON = "AcceptInvitationButton"
    const val EVENT_TITLE = "EventTitle"
    const val EVENT_PLACE = "EventPlace"
    const val EVENT_DATE = "EventDate"
    const val NUMBER_OF_PEOPLE = "NumberOfPeople"

    //Extension
    const val DAY_THRESHOLD = 7
    const val PADDING_ZERO_THRESHOLD = 10

    //BottomNavigation bar
    const val BOTTOM_NAVIGATION_BAR = "BottomNavigationBar"
    const val BOTTOM_NAVIGATION_BAR_ITEM = "BottomNavigationBarItem"

    //Main screen
    const val TOP_BAR = "TopBar"
}