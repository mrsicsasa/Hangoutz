package com.example.hangoutz.utils

object Constants {
    //SharedPreferences
    const val PREF_NAME = "hangoutz-shared-preferences"
    const val KEY_USER_ID = "user_id"

    //Splash screen
    const val LOGO_ANIMATION_DELAY_SPLASH: Long = 800
    const val LOGO_ANIMATION_DURATION: Int = 500
    const val BACKGROUND_ANIMATION_DURATION: Int = 2000
    const val LOGO_ANIMATION_DELAY: Long = 100
    const val SPLASH_SCREEN_BACKGROUND = "splashScreenBackground"
    const val SPLASH_SCREEN_LOGO = "splashScreenLogo"

    //LOGIN
    const val LOGIN_SIGN_IN_BUTTON = "loginSignInButton"
    const val LOGIN_CREATE_ACCOUNT = "loginCreateAccount"
    const val LOGIN_EMAIL_INPUT_FIELD = "loginEmailInputField"
    const val LOGIN_PASSWORD_INPUT_FIELD = "loginPasswordInputField"
    const val LOGIN_ICON = "loginIcon"

    //credentials
    const val EMAIL = "Email"
    const val PASSWORD = "Password"
    const val LOGIN = "Login"
    const val LOGOUT = "Logout"

    //Top bar
    const val TOP_BAR_TITLE = "Hangoutz"

    //Error codes
    const val DUPLICATE_ITEM = 409

    //Errors
    const val ERROR_EMPTY_FIELDS = "All fields must be filled!"
    const val ERROR_INVALID_INPUT = "Incorrect email or password"

    //Events screen
    const val GET_EVENTS_ERRORS = "Loading events errors"
    const val CREATE_EVENT_BUTTON = "createEventButton"
    const val FILTER_BAR_ITEM_COUNT: Int = 3
    const val INVITE_STATUS_ACCEPTED = "accepted"
    const val INVITE_STATUS_INVITED = "invited"
    const val HORIZONTAL_PAGER= "horizontalPager"
    const val EVENT_STATUS_ACCEPTED = "accepted"
    const val EVENT_STATUS_DECLINED = "declined"

    //EventCard
    const val EVENT_CARD = "eventCard"
    const val LOGO_BACKGROUND = "logoBackground"
    const val LOGO_IMAGE = "logoImage"
    const val DECLINE_INVITATION_BUTTON = "declineInvitationButton"
    const val ACCEPT_INVITATION_BUTTON = "acceptInvitationButton"
    const val EVENT_TITLE = "eventTitle"
    const val EVENT_PLACE = "eventPlace"
    const val EVENT_DATE = "eventDate"
    const val NUMBER_OF_PEOPLE = "numberOfPeople"
    const val NUMBER_OF_PEOPLE_THRESHOLD = 0
    const val NUMBER_OF_PEOPLE_ADD_OWNER = 1

    const val SCREEN_SIZE_THRESHOLD = 400

    //Extension
    const val DAY_THRESHOLD = 7
    const val PADDING_ZERO_THRESHOLD = 10

    //Component tags
    const val ACTION_BUTTON_TEXT = "actionButtonText"
    const val ACTION_BUTTON_ICON = "actionButtonIcon"

    //Test tags settings screen
    const val SETTINGS_NAME_FIELD_TAG = "settingsNameField"
    const val SETTINGS_NAME_ICON_TAG = "settingsNameIcon"
    const val SETTINGS_USER_PHOTO_TAG = "settingsUserPhoto"
    const val SETTINGS_BACKGROUND_LINES_TAG = "settingsBackgroundLines"
    const val SETTINGS_EMAIL_FIELD_TAG = "settingsEmailField"
    const val SETTINGS_LOGOUT_BUTTON = "settingsLogoutButton"
    const val DEFAULT_USER_PHOTO = "avatar_default.png"

    //Settings page
    const val PROFILE_PHOTO = "Profile Photo"

    //BottomNavigation bar
    const val BOTTOM_NAVIGATION_BAR = "bottomNavigationBar"
    const val BOTTOM_NAVIGATION_BAR_ITEM = "bottomNavigationBarItem"

    //Main screen
    const val TOP_BAR = "topBar"

    //Invite respond button
    const val INVITE_RESPOND_BUTTON_TEXT = "inviteRespondButtonText"

    //Validator strings
    const val AT_SIGN = "@"
    const val VALID_EMAIL = "@gmail.com"
    const val CONTAINS_DIGIT = ".*\\d.*"

    //RegisterViewModel
    const val MIN_NAME_LENGTH = 3
    const val MAX_NAME_LENGTH = 25
    const val MIN_PASSWORD_LENGTH = 8
    const val TWO_HALVES = 2
    const val NAME_ERROR_MESSAGE = "Name must be 3–25 characters"
    const val EMAIL_FORMAT_ERROR_MESSAGE = "Email must be in correct format"
    const val EMAIL_DUPLICATE_ERROR_MESSAGE = "Email already in use"
    const val PASSWORD_ERROR_MESSAGE = "Password must contain at least one number and be minimum 8 characters long"
    const val CONFIRM_PASSWORD_ERROR_MESSAGE = "Passwords must match"
    const val GENERIC_ERROR_MESSAGE = "An error has occurred"

    //Register tags
    const val REGISTER_BACKGROUND_COLUMN = "registerBackgroundColumn"
    const val REGISTER_LOGO = "registerLogo"
    const val REGISTER_NAME_INPUT = "registerNameInput"
    const val REGISTER_NAME_ERROR = "registerNameError"
    const val REGISTER_EMAIL_INPUT = "registerEmailInput"
    const val REGISTER_EMAIL_ERROR = "registerEmailError"
    const val REGISTER_PASSWORD_INPUT = "registerPasswordInput"
    const val REGISTER_PASSWORD_ERROR = "registerPasswordError"
    const val REGISTER_CONFIRM_PASSWORD_INPUT = "registerConfirmPasswordInput"
    const val REGISTER_CONFIRM_PASSWORD_ERROR = "registerConfirmPasswordError"
    const val REGISTER_INCOMPLETE_FORM_ERROR = "registerIncompleteFormError"
    const val REGISTER_CREATE_ACCOUNT_BUTTON = "registerCreateAccountButton"

    //Friends tags
    const val FRIENDS_BACKGROUND_BOX = "friendsBackgroundBox"
    const val FRIENDS_LIST_ELEMENT = "friendsListElement"
    const val FRIENDS_PROFILE_PICTURE_DESCRIPTION = "Author's profile photo"
    const val FRIENDS_LIST_PHOTO = "friendsListPhoto"
    const val FRIENDS_LIST_NAME = "friendsListName"
    const val FRIENDS_ADD_BUTTON = "friendsAddButton"
}