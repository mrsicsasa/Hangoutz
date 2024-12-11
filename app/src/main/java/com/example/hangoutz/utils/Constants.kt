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
    const val ERROR_EMPTY_FIELD = "Fields marked with * must be filled"
    const val ERROR_TOO_LONG = "This field can have max 25 characters"
    const val ERROR_TOO_LONG_DESC = "Description can have max 100 characters"

    //Events screen
    const val GET_EVENTS_ERRORS = "Loading events errors"
    const val CREATE_EVENT_BUTTON = "createEventButton"
    const val FILTER_BAR_ITEM_COUNT: Int = 3
    const val INVITE_STATUS_ACCEPTED = "accepted"
    const val INVITE_STATUS_INVITED = "invited"
    const val HORIZONTAL_PAGER = "horizontalPager"
    const val EVENT_STATUS_ACCEPTED = "accepted"
    const val EVENT_STATUS_DECLINED = "declined"
    const val NO_EVENTS_AVAILABLE_MESSAGE = "noEventsAvailableMessage"
    const val EVENTS_LOADING_SPINNER = "eventsLoadingSpinner"
    const val FILTER_BAR_ITEM = "filterBarItem"
    const val ANIMATION_TAB_TEXT_COLOR = "animationTabTextColor"
    const val ANIMATION_INDICATOR_OFFSET = "animationIndicatorOffset"
    const val INVITED_TAB_INDEX = 1
    const val NUMBER_OF_INVITES_DEFAULT_VALUE = 0
    const val EVENT_EDITED_SUCCESSFULLY = "Event Edited Successfully"

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
    const val NUMBER_OF_PEOPLE_THRESHOLD = 1

    //Extension
    const val DAY_THRESHOLD = 7
    const val PADDING_ZERO_THRESHOLD = 10

    //Component tags
    const val ACTION_BUTTON_TEXT = "actionButtonText"
    const val ACTION_BUTTON_ICON = "actionButtonIcon"

    //Test tags settings screen
    const val SETTINGS_NAME_FIELD_TAG = "settingsNameField"
    const val SETTINGS_NAME_VALIDATOR_ICON_TAG = "settingsNameValidatorIcon"
    const val SETTINGS_USER_PHOTO_TAG = "settingsUserPhoto"
    const val SETTINGS_BACKGROUND_LINES_TAG = "settingsBackgroundLines"
    const val SETTINGS_EMAIL_FIELD_TAG = "settingsEmailField"
    const val SETTINGS_LOGOUT_BUTTON = "settingsLogoutButton"
    const val DEFAULT_USER_PHOTO = "avatar_default.png"

    //Settings page
    const val PROFILE_PHOTO = "Profile Photo"
    const val SETTINGS_CAMERA = "Capture from Camera"
    const val SETTINGS_GALLERY = "Pick from gallery"
    const val SETTINGS_ACTION = "Choose Action"
    const val RANDOM_STRING_LENGTH = 3
    const val JPG = ".jpg"
    const val TEMP_IMAGE = "_image"

    //BottomNavigation bar
    const val BOTTOM_NAVIGATION_BAR = "bottomNavigationBar"
    const val BOTTOM_NAVIGATION_BAR_ITEM = "bottomNavigationBarItem"

    //Main screen
    const val TOP_BAR = "topBar"

    //Invite respond button
    const val INVITE_RESPOND_BUTTON_TEXT = "inviteRespondButtonText"

    //Validator strings
    const val AT_SIGN = "@"
    const val VALID_EMAIL = "^[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.com$"
    const val CONTAINS_DIGIT = ".*\\d.*"

    //Input fields
    const val MAX_LINES = 3

    //RegisterViewModel
    const val MIN_NAME_LENGTH = 3
    const val MAX_NAME_LENGTH = 25
    const val MIN_PASSWORD_LENGTH = 8
    const val TWO_HALVES = 2
    const val NAME_ERROR_MESSAGE = "Name must be 3–25 characters"
    const val EMAIL_FORMAT_ERROR_MESSAGE = "Email must be in correct format"
    const val EMAIL_DUPLICATE_ERROR_MESSAGE = "Email already in use"
    const val PASSWORD_ERROR_MESSAGE =
        "Password must contain at least one number and be minimum 8 characters long"
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

    //FriendsViewModel
    const val MIN_SEARCH_LENGTH = 3

    //Friends tags
    const val FRIENDS_BACKGROUND_BOX = "friendsBackgroundBox"
    const val FRIENDS_LIST_ELEMENT = "friendsListElement"
    const val FRIENDS_PROFILE_PICTURE_DESCRIPTION = "Author's profile photo"
    const val FRIENDS_LIST_PHOTO = "friendsListPhoto"
    const val FRIENDS_LIST_NAME = "friendsListName"
    const val FRIENDS_ADD_BUTTON = "friendsAddButton"

    //TEST TAGS - CREATE EVENT
    const val CREATE_EVENT_TITLE_FIELD = "createEventTitleField"
    const val CREATE_EVENT_DESC_FIELD = "createEventDescriptionField"
    const val CREATE_EVENT_CITY_FIELD = "createEventCityField"
    const val CREATE_EVENT_STREET_FIELD = "createEventStreetField"
    const val CREATE_EVENT_PLACE_FIELD = "createEventPlaceField"
    const val CREATE_EVENT_DATE_FIELD = "createEventDateField"
    const val CREATE_EVENT_TIME_FIELD = "createEventTimeField"
    const val CREATE_EVENT_ADD_PARTICIPANTS_BUTTON = "createEventAddParticipantsButton"
    const val CREATE_EVENT_CREATE_BUTTON = "createEventCreateButton"

    //FriendsViewModel
    const val FRIENDS_SEARCH_BAR_CLEAR_DESCRIPTION = "Clear search bar icon"

    //Friends tags
    const val FRIENDS_SEARCH_BAR = "friendsSearchBar"
    const val FRIENDS_LOADING_SPINNER = "friendsLoadingSpinner"
    const val NO_FRIENDS_AVAILABLE_MESSAGE = "noFriendsAvailableMessage"
    const val CREATE_EVENT_ADD_SELECTED_PARTICIPANTS_BUTTON = "createEventAddSelectedParticipantsButton"

    //Bottom sheet
    const val BOTTOM_SHEET_TAG = "bottomSheet"
    const val BOTTOM_SHEET_SEARCH = "bottomSheetSearch"
    const val BOTTOM_SHEET_USER_ROW = "bottomSheetUserRow"
    const val BOTTOM_SHEET_PROFILE_PICTURE = "bottomSheetProfilePicture"
    const val BOTTOM_SHEET_USERNAME = "bottomSheetUsername"
    const val BOTTOM_SHEET_ADD_ICON = "bottomSheetAddIcon"
    const val BOTTOM_SHEET_LOADING_SPINNER = "bottomSheetLoadingSpinner"
    const val NO_USERS_FOUND_MESSAGE = "noFriendsFoundMessage"
    const val BOTTOM_SHEET_SEARCH_MESSAGE = "bottomSheetSearchMessage"

    //Event details test tags
    const val PARTICIPANT_ICON_TAG = "participantIconTag"
    const val PARTICIPANT_IMAGE = "participantImage"
    const val PARTICIPANT_DIVIDER = "participantDivider"

    const val EVENT_DETAILS_TITLE = "eventDetailsTitle"
    const val EVENT_DETAILS_DESC = "eventDetailsDescription"
    const val EVENT_DETAILS_PLACE = "eventDetailsPlace"
    const val EVENT_DETAILS_STREET = "eventDetailsStreet"
    const val EVENT_DETAILS_CITY = "eventDetailsCity"
    const val EVENT_DETAILS_DATE = "eventDetailsDate"
    const val EVENT_DETAILS_TIME = "eventDetailsTime"

    const val EVENT_OWNER_TITLE_FIELD = "eventOwnerTitleField"
    const val EVENT_OWNER_DESC_FIELD = "eventOwnerDescriptionField"
    const val EVENT_OWNER_CITY_FIELD = "eventOwnerCityField"
    const val EVENT_OWNER_STREET_FIELD = "eventOwnerStreetField"
    const val EVENT_OWNER_PLACE_FIELD = "eventOwnerPlaceField"
    const val EVENT_OWNER_DATE_FIELD = "eventOwnerDateField"
    const val EVENT_OWNER_TIME_FIELD = "eventOwnerTimeField"
    const val EVENT_OWNER_ADD_PARTICIPANTS_BUTTON = "eventOwnerAddParticipantsButton"
    const val EVENT_OWNER_EDIT_BUTTON = "eventOwnerCreateButton"
    const val CREATE_EVENT_REMOVE_BUTTON = "createEventRemoveButton"

    const val DELETE_ERROR = "An error has ocurred while deleting"
    const val DELETE_SUCCESS = "Successfully deleted the event"

    const val MAX_LENGTH = 25
    const val MAX_LENGTH_DESC = 100

    const val DATE_IN_PAST = "Date and time cannot be in the past"

}