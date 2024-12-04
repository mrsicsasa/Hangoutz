package com.example.hangoutz.ui.screens.eventDetailsOwner

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
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
import com.example.hangoutz.ui.components.DatePickerModal
import com.example.hangoutz.ui.components.InputField
import com.example.hangoutz.ui.components.InputFieldWithIcon
import com.example.hangoutz.ui.components.ParticipantUI
import com.example.hangoutz.ui.components.TimePickerModal
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.ui.theme.TopBarBackgroundColor
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventOwnerDetailsScreen(
    navController: NavController,
    viewmodel: EventDetailsOwnerViewModel = hiltViewModel()
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = Dimensions.CREATE_EVENT_ICON_PADDING),
                    horizontalArrangement = Arrangement.End

                ) {
                    Icon(painter = painterResource(id = R.drawable.trashicon),
                        contentDescription = "Image",
                        tint = Ivory,
                        modifier = Modifier.clickable { viewmodel.deleteEvent() })
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = TopBarBackgroundColor
            ),
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
                modifier = Modifier.padding(
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
                            { viewmodel.onTitleChange(it) },
                            modifier = Modifier.semantics {
                                contentDescription = Constants.EVENT_OWNER_TITLE_FIELD
                            },
                            true
                        )
                    }

                    data.value.description?.let {
                        InputField(
                            stringResource(R.string.event_desc),
                            it,
                            { viewmodel.onDescriptionChange(it) },
                            modifier = Modifier.semantics {
                                contentDescription = Constants.EVENT_OWNER_DESC_FIELD
                            },
                            true
                        )
                    }

                    data.value.city?.let {
                        InputField(
                            stringResource(R.string.event_city),
                            it,
                            { viewmodel.onCityChange(it) },
                            modifier = Modifier.semantics {
                                contentDescription = Constants.EVENT_OWNER_CITY_FIELD
                            },
                            true
                        )
                    }
                    data.value.street?.let {
                        InputField(
                            stringResource(R.string.event_street),
                            it,
                            { viewmodel.onStreetChange(it) },
                            modifier = Modifier.semantics {
                                contentDescription = Constants.EVENT_OWNER_STREET_FIELD
                            },
                            true
                        )
                    }

                    data.value.place?.let {
                        InputField(
                            stringResource(R.string.event_place),
                            it,
                            { viewmodel.onPlaceChange(it) },
                            modifier = Modifier.semantics {
                                contentDescription = Constants.EVENT_OWNER_PLACE_FIELD
                            },
                            true
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimensions.CREATE_EVENT_VERTICAL_PADDING),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Dimensions.CREATE_EVENT_HORIZONTAL_SPACING)
                    ) {

                        data.value.date?.let {
                            InputFieldWithIcon(
                                stringResource(R.string.event_date),
                                it,
                                { viewmodel.onDateChange(it) },
                                modifier = Modifier
                                    .weight(1f)
                                    .semantics {
                                        contentDescription = Constants.EVENT_OWNER_DATE_FIELD
                                    },
                                R.drawable.calendaricon,
                                true,
                                true,
                                { viewmodel.setShowDatePicker() })
                        }

                        data.value.time?.let {
                            InputFieldWithIcon(
                                stringResource(R.string.event_time),
                                it,
                                { viewmodel.onTimeChange(it) },
                                modifier = Modifier
                                    .weight(1f)
                                    .semantics {
                                        contentDescription = Constants.EVENT_OWNER_TIME_FIELD
                                    },
                                R.drawable.clockicon,
                                true,
                                true,
                                { viewmodel.setShowTimePicker() })
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimensions.CREATE_EVENT_VERTICAL_PADDING),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            stringResource(R.string.event_participants),
                            color = Ivory,
                            modifier = Modifier.padding(
                                top = Dimensions.CREATE_EVENT_TEXT_PADDING,
                                bottom = Dimensions.CREATE_EVENT_TEXT_PADDING
                            ),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Image(painter = painterResource(id = R.drawable.addevent),
                            contentDescription = "",
                            modifier = Modifier
                                .clickable { }
                                .semantics {
                                    contentDescription =
                                        Constants.EVENT_OWNER_ADD_PARTICIPANTS_BUTTON
                                })
                    }
                    HorizontalDivider(
                        thickness = Dimensions.CREATE_EVENT_LINE_THICKNESS,
                        color = Ivory
                    )
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
                    //TODO put participants here, use participantUI component (check event details screen)
                }
            }
            ActionButton(
                stringResource(R.string.event_edit),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = Dimensions.ACTION_BUTTON_MEDIUM3)
                    .semantics {
                        contentDescription = Constants.EVENT_OWNER_EDIT_BUTTON
                    },
                onClick = {
                    viewmodel.editEvent()
                })
        }
    }

    if (data.value.showDatePicker) {
        DatePickerModal(onDateSelected = { date ->
            date?.let {
                viewmodel.onDatePicked(date)
            }
        }, onDismiss = { viewmodel.setShowDatePicker() })
    }

    if (data.value.showTimePicker) {
        TimePickerModal(onConfirm = { time ->
            viewmodel.onTimePicked(time)
            viewmodel.setShowTimePicker()
        }, onDismiss = { viewmodel.setShowTimePicker() })
    }
}