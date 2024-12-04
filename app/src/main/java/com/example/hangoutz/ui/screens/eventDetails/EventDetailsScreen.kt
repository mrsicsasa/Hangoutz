package com.example.hangoutz.ui.screens.eventDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.ActionButton
import com.example.hangoutz.ui.components.InputField
import com.example.hangoutz.ui.components.InputFieldWithIcon
import com.example.hangoutz.ui.components.ParticipantUI
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.ui.theme.TopBarBackgroundColor
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Constants.DEFAULT_USER_PHOTO
import com.example.hangoutz.utils.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(
    navController: NavController,
    eventId: String?,
    viewmodel: EventDetailsViewModel = hiltViewModel()
) {
    val data = viewmodel.uiState.collectAsState()
    viewmodel.getEventIdFromController(navController)

    val scrollableField =
        LocalConfiguration.current.screenHeightDp.dp - (LocalConfiguration.current.screenHeightDp.dp - Dimensions.ACTION_BUTTON_MEDIUM4)

    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier
                .height(Dimensions.TOP_BAR_HEIGHT)
                .wrapContentHeight(align = Alignment.CenterVertically)
                .semantics {
                    contentDescription = Constants.TOP_BAR
                },
            title = {
                Text(
                    text = Constants.TOP_BAR_TITLE,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.semantics {
                        contentDescription = Constants.TOP_BAR_TITLE
                    },
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = TopBarBackgroundColor
            )
        )
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(R.drawable.blurred_background),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            Box(
                modifier = Modifier
                    .padding(
                        top = innerPadding.calculateTopPadding() + Dimensions.EVENTDETAILS_TOP_PADDING,
                        start = Dimensions.ACTION_BUTTON_MEDIUM2,
                        end = Dimensions.ACTION_BUTTON_MEDIUM2,
                        bottom = scrollableField
                    )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    data.value.title?.let {
                        InputField(
                            stringResource(R.string.event_title),
                            it,
                            { },
                            modifier = Modifier.semantics {
                                contentDescription = Constants.LOGIN_EMAIL_INPUT_FIELD
                            }
                        )
                    }


                    data.value.description?.let {
                        InputField(
                            stringResource(R.string.event_desc),
                            it,
                            { },
                            modifier = Modifier.semantics {
                                contentDescription = Constants.LOGIN_EMAIL_INPUT_FIELD
                            }
                        )
                    }

                    data.value.city?.let {
                        InputField(
                            stringResource(R.string.event_city),
                            it,
                            { },
                            modifier = Modifier.semantics {
                                contentDescription = Constants.LOGIN_EMAIL_INPUT_FIELD
                            }
                        )
                    }


                    data.value.street?.let {
                        InputField(
                            stringResource(R.string.event_street),
                            it,
                            { },
                            modifier = Modifier.semantics {
                                contentDescription = Constants.LOGIN_EMAIL_INPUT_FIELD
                            }
                        )
                    }


                    data.value.place?.let {
                        InputField(
                            stringResource(R.string.event_place),
                            it,
                            { },
                            modifier = Modifier.semantics {
                                contentDescription = Constants.LOGIN_EMAIL_INPUT_FIELD
                            }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(35.dp)
                    ) {
                        data.value.date?.let {
                            InputField(
                                stringResource(R.string.event_date),
                                it,
                                { },
                                modifier = Modifier
                                    .weight(1f)
                                    .semantics {
                                        contentDescription = Constants.LOGIN_EMAIL_INPUT_FIELD
                                    },
                                false,
                                true,
                            )
                        }
                        data.value.time?.let {
                            InputField(
                                stringResource(R.string.event_time),
                                it,
                                { },
                                modifier = Modifier
                                    .weight(1f)
                                    .semantics {
                                        contentDescription = Constants.LOGIN_EMAIL_INPUT_FIELD
                                    },
                                false,
                                true,
                            )
                        }
                    }

                    Text(
                        "Participants",
                        color = Ivory,
                        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    HorizontalDivider(thickness = 2.dp, color = Ivory)
                    LaunchedEffect(data.value.eventId) {
                        data.value.eventId?.let {
                            viewmodel.getParticipants()
                        }
                    }
                    val participants = data.value.participants
                    participants.forEach { participant ->
                        ParticipantUI(
                            participant = participant,
                            false,
                            {}
                        )

                    }
                }
            }
            ActionButton(
                "Leave Event",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = Dimensions.ACTION_BUTTON_MEDIUM3),
                onClick = {
                    viewmodel.leaveEvent {
                    }
                }

            )

        }
    }


}










