package com.example.hangoutz.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object Dimensions {
    //Events
    val CONTENT_TOP_PADDING: Dp = 40.dp
    val CONTENT_START_PADDING: Dp = 15.dp
    val CONTENT_END_PADDING: Dp = 5.dp
    val SPACE_HEIGHT_BETWEEN_CARDS: Dp = 20.dp

    //Floating Button
    val FLOATING_BUTTON_PADDING = 10.dp
    val FLOATING_ICON_SIZE: Dp = 40.dp
    val FILTER_BAR_TOP_PADDING = 10.dp
    val FLOATING_BUTTON_BORDER = 2.dp

    //Search bar
    val SEARCH_BAR_HEIGHT = 50.dp
    val SEARCH_BAR_CLEAR_ICON_SIZE = 20.dp
    val SEARCH_BAR_BORDER = 1.5.dp
    val SEARCH_BAR_HORIZONTAL_PADDING = 20.dp

    //Bottom popup sheet
    val BOTTOM_SHEET_HORIZONTAL_PADDING = 30.dp
    val BOTTOM_SHEET_VERTICAL_PADDING = 40.dp
    val BOTTOM_SHEET_USER_PADDING = 10.dp
    val BOTTOM_SHEET_DIVIDER_WIDTH = 2.dp
    val BOTTOM_SHEET_NAME_PADING = 12.dp
    val ADD_ICON_SIZE = 30.dp

    //InviteRespondButton
    val INVITE_RESPOND_BUTTON_ROUNDED_RADIUS: Dp = 20.dp
    val INVITE_RESPOND_BUTTON_WIDTH: Dp = 105.dp
    val INVITE_RESPOND_BUTTON_FONT_SIZE = 12.sp

    //EventCard
    val CARD_ROUNDED_CORNER_RADIUS: Dp = 16.dp
    val CARD_HEIGHT: Dp = 135.dp
    val CARD_END_PADDING: Dp = 10.dp
    val CARD_ROW_HORIZONTAL_PADDING: Dp = 16.dp
    val CARD_ROW_TOP_PADDING: Dp = 16.dp
    val SPACE_BETWEEN_IMAGE_AND_TEXT: Dp = 10.dp
    val SPACE_BETWEEN_PLACE_AND_DATE: Dp = 5.dp
    val INVITATION_BOX_END_PADDING: Dp = 20.dp
    val INVITATION_BOX_BOTTOM_PADDING: Dp = 10.dp
    val INVITATION_BUTTONS_SEPARATION_WIDTH = 10.dp
    val INVITE_RESPOND_BUTTON_INNER_PADDING = 2.dp
    val ANIMATION_TARGET_VALUE = 360f
    val ANIMATION_DURATION = 3000

    //Profile image
    val PROFILE_IMAGE_SIZE: Dp = 74.dp
    val PROFILE_IMAGE_BORDER_WIDTH: Dp = 2.dp

    //Splash screen
    const val SPLASH_SCREEN_STARTING_ALPHA = 0.4f
    const val SPLASH_SCREEN_TARGETED_ALPHA = 1f
    const val SPLASH_SCREEN_LOGO_INITIAL_ALPHA = 1f
    const val SPLASH_SCREEN_LOGO_TARGETED_ALPHA = 0f

    // Register Screen
    val REGISTER_BOTTOM_PADDING = 145.dp
    val REGISTER_FORM_SIDE_PADDING = 30.dp
    val REGISTER_FORM_BOTTOM_PADDING = 50.dp

    // Logo
    const val LOGO_ASPECT_RATIO = 1f
    val LOGO_HEIGHT = 50.dp
    const val LOGO_OVERSHOOT_INTERPOLATOR_TENSION = 0f

    // BottomBarNavigation
    val BOTTOM_NAVIGATION_BAR_HEIGHT = 67.dp
    val BOTTOM_NAVIGATION_ICON_SIZE = 30.dp

    //Top bar
    val TOP_BAR_HEIGHT = 55.dp

    //Large range 100.dp and above
    val SETTINGS_SCREEN_LARGE1 = 215.dp
    val SETTINGS_SCREEN_LARGE2 = 200.dp

    //Medium range 20-99.dp
    val SETTINGS_SCREEN_MEDIUM1 = 45.dp
    val SETTINGS_SCREEN_MEDIUM2 = 70.dp
    val SETTINGS_SCREEN_MEDIUM3 = 50.dp
    val SETTINGS_SCREEN_MEDIUM4 = 25.dp

    //Small range 1-19.dp
    val SETTINGS_SCREEN_SMALL1 = 5.dp
    val SETTINGS_SCREEN_SMALL2 = 15.dp
    val SETTINGS_SCREEN_SMALL3 = 2.5f.dp
    val SETTINGS_SCREEN_SMALL4 = 10.dp

    //Small range 1-19.dp
    val ACTION_BUTTON_SMALL1 = 10.dp
    val ACTION_BUTTON_SMALL2 = 5.dp

    //Medium range 20-99.dp

    val ACTION_BUTTON_MEDIUM1 = 50.dp
    val ACTION_BUTTON_MEDIUM2 = 30.dp
    val ACTION_BUTTON_MEDIUM3 = 20.dp
    val ACTION_BUTTON_MEDIUM4 = 80.dp

    //Error message
    val ERROR_MESSAGE_PADDING_SMALL = 10.dp

    //Input Field
    val INPUT_FIELD_ROUNDED_CORNERS = 20.dp
    val INPUT_FIELD_PADDING_SMALL = 10.dp

    //Friends Popup
    val POPUP_HEIGHT = 690.dp
    val POPUP_WIDTH = 350.dp

    // Filter bar

    const val FILTER_BAR_ALPHA = 0.46f
    const val BAR_WIDTH_SCREEN_PERCENT = 0.9f
    const val BAR_HEIGHT_SCREEN_PERCENT = 0.05f
    val BADGE_SIZE = 18.dp
    val BADGE_FONT_SIZE = 10.sp
    val TAB_ITEM_FONT_SIZE = 12.sp
    const val TAB_HEIGHT_PERCENT = 0.7f
    const val INDICATOR_POSITION_OFFSET = 0.1f
    const val INDICATOR_POSITION_INVITED = 0.07f
    const val INDICATOR_Y_OFFSET = 0.16f
    const val INDICATOR_WIDTH_PERCENT = 0.8f
    const val INDICATOR_INVITED_WIDTH_PERCENT = 0.7f
    const val SLIDE_ANIMATION_DURATION = 1000

    //Friends Screen
    val FRIENDS_OUTER_PADDING = 10.dp
    val FRIENDS_HORIZONTAL_PADDING = 15.dp
    val FRIENDS_FIELD_VERTICAL_PADDING = 20.dp
    val FRIENDS_FIELD_ROUNDED_CORNER = 20.dp
    val FRIENDS_INNER_VERTICAL_PADDING = 10.dp
    val FRIENDS_AVATAR_SIZE = 40.dp
    val FRIENDS_AVATAR_BORDER = 3.dp
    val FRIENDS_TEXT_START_PADDING = 10.dp
    val FRIENDS_FLOATING_BUTTON_PADDING = 20.dp
    val FRIENDS_LOADING_SPINNER_SIZE = 40.dp
    val DISMISS_ICON_END_PADDING = 8.dp
    const val DISMISS_POSITIONAL_THRESHOLD = .25f
    val SPACER_FROM_FRIEND_UP = 10.dp
    val SPACER_FROM_FRIEND_DOWN = 5.dp
    val FRIENDS_POPUP_PARTICIPANT_COLUMN = 0.85f
    val FRIENDS_POPUP_COLUMN = 1f
    val BUTTON_COLUMN_BOTTOM_PADDING = 10.dp
    val BUTTON_INNER_PADDING = 3.dp
    val BUTTON_WIDTH = 188.dp
    val BUTTON_HEIGHT = 53.dp
    val ADD_FONT_WEIGHT = 700
    val ADD_FONT_SIZE = 20.sp

    //CREATE EVENT
    val CREATE_EVENT_HORIZONTAL_SPACING = 35.dp
    val CREATE_EVENT_VERTICAL_PADDING = 2.dp
    val CREATE_EVENT_TEXT_PADDING = 15.dp
    val CREATE_EVENT_LINE_THICKNESS = 2.dp
    val CREATE_EVENT_ICON_PADDING = 2.dp
    val CREATE_EVENT_PARTICIPANT = 10.dp
    val CREATE_EVENT_PARTICIPANT_PHOTO = 40.dp
    val CREATE_EVENT_TEXT_PADDING2 = 12.dp

    //TIME PICKER
    val TIME_PICKER_CORNERS = 20.dp

    //EVENT DETAILS SCREEN
    val EVENTDETAILS_TOP_PADDING = 35.dp

}


