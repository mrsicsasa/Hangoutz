package com.example.hangoutz.ui.screens.events

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.ProfileScreen
import com.example.hangoutz.ui.theme.Blue
import com.example.hangoutz.ui.theme.GreenMinty
import com.example.hangoutz.ui.theme.textBodyGrayColor
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions

@Composable
fun EventCard(
    backgroundColor: Color,
    imageUrl: String,
    title: String,
    place: String,
    date: String,
    countOfPeople: Int,
    isInvited: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardColors(
            containerColor = backgroundColor,
            contentColor = Color.White,
            disabledContainerColor = Color.Red,
            disabledContentColor = Color.Red
        ),
        shape = RoundedCornerShape(Dimensions.CARD_ROUNDED_CORNER_RADIUS),
        modifier = modifier
            .fillMaxWidth()
            .height(Dimensions.CARD_HEIGHT)
            .padding(end = Dimensions.CARD_END_PADDING)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = Dimensions.CARD_ROW_HORIZONTAL_PADDING)
                .padding(top = Dimensions.CARD_ROW_TOP_PADDING)
        ) {
            ProfileScreen(
                imageUrl = imageUrl,
                boxModifier = Modifier.testTag(Constants.LOGO_BACKGROUND),
                imageModifier = Modifier.testTag(Constants.LOGO_IMAGE)
            )
            Spacer(modifier = Modifier.width(Dimensions.SPACE_BETWEEN_IMAGE_AND_TEXT))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displayLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.testTag(Constants.EVENT_TITLE)
                )
                Text(
                    text = "@ ${place}",
                    style = MaterialTheme.typography.displayMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.testTag(Constants.EVENT_PLACE)
                )
                Spacer(modifier = Modifier.height(Dimensions.SPACE_BETWEEN_PLACE_AND_DATE))
                Text(
                    text = date,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White
                    ),
                    modifier = Modifier.testTag(Constants.EVENT_DATE)
                )
            }
        }
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(
                    end = Dimensions.INVITATION_BOX_END_PADDING,
                    bottom = Dimensions.INVITATION_BOX_BOTTOM_PADDING
                )
        ) {
            if (isInvited) {
                Row {
                    InviteRespondButton(
                        backgroundColor = Blue,
                        fontColor = Color.White,
                        title = stringResource(R.string.decline_button_text),
                        onClick = {},
                        modifier = Modifier.testTag(Constants.DECLINE_INVITATION_BUTTON)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    InviteRespondButton(
                        backgroundColor = GreenMinty,
                        fontColor = Color.Black,
                        title = stringResource(R.string.accept_button_text),
                        onClick = {},
                        modifier = Modifier.testTag(Constants.ACCEPT_INVITATION_BUTTON)
                    )
                }
            } else {
                Text(
                    text = if (countOfPeople > 0) stringResource(
                        R.string.people_going,
                        countOfPeople
                    ) else stringResource(R.string.no_one_is_going),
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = textBodyGrayColor,
                    ),
                    modifier = Modifier.testTag(Constants.NUMBER_OF_PEOPLE)
                )
            }
        }
    }
}
