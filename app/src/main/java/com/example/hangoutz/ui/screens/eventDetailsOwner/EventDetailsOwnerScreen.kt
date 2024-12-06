package com.example.hangoutz.ui.screens.eventDetailsOwner

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.ActionButton
import com.example.hangoutz.ui.components.DatePickerModal
import com.example.hangoutz.ui.components.ErrorMessage
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
    navController: NavController, viewmodel: EventDetailsOwnerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val data = viewmodel.uiState.collectAsState()
    viewmodel.getEventIdFromController(navController)

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
                        modifier = Modifier.clickable {
                            viewmodel.deleteEvent(
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        Constants.DELETE_SUCCESS,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.popBackStack()
                                },
                                onError = { errorMessage ->
                                    Toast.makeText(
                                        context,
                                        Constants.DELETE_ERROR,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        })
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = TopBarBackgroundColor
            ),
        )
    }) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(R.drawable.blurred_background),
                    contentScale = ContentScale.FillBounds
                )
        ) {

            Column(
                modifier = Modifier
                    .padding(
                        top = innerPadding.calculateTopPadding() + Dimensions.EVENT_DETAILS_TOP_PADDING,
                        start = Dimensions.ACTION_BUTTON_MEDIUM2,
                        end = Dimensions.ACTION_BUTTON_MEDIUM2,
                        bottom = Dimensions.ACTION_BUTTON_SMALL1
                    )
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .weight(1f)
            ) {
                data.value.title?.let {
                    InputField(
                        stringResource(R.string.event_title),
                        it,
                        { viewmodel.onTitleChange(it) },
                        modifier = Modifier.semantics {
                            contentDescription = Constants.EVENT_OWNER_TITLE_FIELD
                        },
                        true,
                        false,
                        data.value.isTitleError
                    )
                }
                data.value.errorTitle.takeIf { it.isNotBlank() }?.let { ErrorMessage(it) }
                data.value.description?.let {
                    InputField(
                        stringResource(R.string.event_desc),
                        it,
                        { viewmodel.onDescriptionChange(it) },
                        modifier = Modifier.semantics {
                            contentDescription = Constants.EVENT_OWNER_DESC_FIELD
                        },
                        true, false, data.value.isDescError
                    )
                }
                data.value.errorDesc.takeIf { it.isNotBlank() }?.let { ErrorMessage(it) }
                data.value.city?.let {
                    InputField(
                        stringResource(R.string.event_city),
                        it,
                        { viewmodel.onCityChange(it) },
                        modifier = Modifier.semantics {
                            contentDescription = Constants.EVENT_OWNER_CITY_FIELD
                        },
                        true, false, data.value.isCityError
                    )
                }
                data.value.errorCity.takeIf { it.isNotBlank() }?.let { ErrorMessage(it) }
                data.value.street?.let {
                    InputField(
                        stringResource(R.string.event_street),
                        it,
                        { viewmodel.onStreetChange(it) },
                        modifier = Modifier.semantics {
                            contentDescription = Constants.EVENT_OWNER_STREET_FIELD
                        },
                        true, false, data.value.isStreetError
                    )
                }
                data.value.errorStreet?.takeIf { it.isNotBlank() }?.let { ErrorMessage(it) }

                data.value.place?.let {
                    InputField(
                        stringResource(R.string.event_place),
                        it,
                        { viewmodel.onPlaceChange(it) },
                        modifier = Modifier.semantics {
                            contentDescription = Constants.EVENT_OWNER_PLACE_FIELD
                        },
                        true,
                        false,
                        data.value.isPlaceError
                    )
                }
                data.value.errorPlace?.takeIf { it.isNotBlank() }?.let { ErrorMessage(it) }

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
                            { viewmodel.setShowDatePicker() },
                            data.value.isDateError
                        )
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
                            { viewmodel.setShowTimePicker() },
                            data.value.isDateError
                        )
                    }
                }
                data.value.errorMessage?.let { ErrorMessage(it) }

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
                                contentDescription = Constants.EVENT_OWNER_ADD_PARTICIPANTS_BUTTON
                            })
                }
                HorizontalDivider(
                    thickness = Dimensions.CREATE_EVENT_LINE_THICKNESS, color = Ivory
                )
                LaunchedEffect(data.value.eventId) {
                    data.value.eventId?.let {
                        viewmodel.getData()
                    }
                }
                val participants = data.value.participants
                participants.forEach { participant ->
                    ParticipantUI(
                        participant = participant,
                        true,
                        { viewmodel.removeUser(participant.id) })
                }

            }
            Column(
            ) {
                ActionButton(stringResource(R.string.event_edit),
                    modifier = Modifier
                        .padding(bottom = Dimensions.ACTION_BUTTON_MEDIUM3)
                        .semantics {
                            contentDescription = Constants.EVENT_OWNER_EDIT_BUTTON
                        },
                    onClick = {
                        viewmodel.editEvent(onSuccess = {
                            Toast.makeText(
                                context,
                                Constants.EVENT_EDITED_SUCCESSFULLY,
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.popBackStack()
                        })
                    })
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
    }
}