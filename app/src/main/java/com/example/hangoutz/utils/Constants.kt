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

    //Top bar
    val TOP_BAR_TITLE = "Hangoutz"

    //Errors
    val ERROR_EMPTY_FIELDS = "All fields must be filled!"
    val ERROR_INVALID_INPUT = "Incorrect email or password"


    //Error codes
    val CONFLICT = 409

    //Errors
    val ERROR_EMPTY_FIELDS = "All fields must be filled!"
    val ERROR_INVALID_INPUT = "Incorrect email or password"
    //Events screen
    val GET_EVENTS_ERRORS = "Loading events errors"
    val CREATE_EVENT_BUTTON = "CreateEventButton"

    //EventCard
    val EVENT_CARD = "EventCard"
    val LOGO_BACKGROUND = "LogoBackground"
    val LOGO_IMAGE = "LogoImage"
    val DECLINE_INVITATION_BUTTON = "DeclineInvitationButton"
    val ACCEPT_INVITATION_BUTTON = "AcceptInvitationButton"
    val EVENT_TITLE = "EventTitle"
    val EVENT_PLACE = "EventPlace"
    val EVENT_DATE = "EventDate"
    val NUMBER_OF_PEOPLE = "NumberOfPeople"
}