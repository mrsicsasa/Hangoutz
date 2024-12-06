package com.example.hangoutz.ui.screens.eventDetails

import android.util.Log
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.ActionButton
import com.example.hangoutz.ui.components.InputField
import com.example.hangoutz.ui.components.ParticipantUI
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.ui.theme.TopBarBackgroundColor
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(
    navController: NavController, viewmodel: EventDetailsViewModel = hiltViewModel()
) {
    val data = viewmodel.uiState.collectAsState()
    viewmodel.getEventIdFromController(navController)

    Scaffold(topBar = {
        TopAppBar(modifier = Modifier
            .height(Dimensions.TOP_BAR_HEIGHT)
            .wrapContentHeight(align = Alignment.CenterVertically)
            .semantics {
                contentDescription = Constants.TOP_BAR
            }, title = {
            Text(
                text = Constants.TOP_BAR_TITLE,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.semantics {
                    contentDescription = Constants.TOP_BAR_TITLE
                },
            )
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TopBarBackgroundColor
        )
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
                    InputField(stringResource(R.string.event_title),
                        it,
                        { },
                        modifier = Modifier.semantics {
                            contentDescription = Constants.EVENT_DETAILS_TITLE
                        })
                }

                data.value.description?.let {
                    InputField(stringResource(R.string.event_desc),
                        it,
                        { },
                        modifier = Modifier.semantics {
                            contentDescription = Constants.EVENT_DETAILS_DESC
                        })
                }

                data.value.city?.let {
                    InputField(stringResource(R.string.event_city),
                        it,
                        { },
                        modifier = Modifier.semantics {
                            contentDescription = Constants.EVENT_DETAILS_CITY
                        })
                }


                data.value.street?.let {
                    InputField(stringResource(R.string.event_street),
                        it,
                        { },
                        modifier = Modifier.semantics {
                            contentDescription = Constants.EVENT_DETAILS_STREET
                        })
                }

                data.value.place?.let {
                    InputField(stringResource(R.string.event_place),
                        it,
                        { },
                        modifier = Modifier.semantics {
                            contentDescription = Constants.EVENT_DETAILS_PLACE
                        })
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimensions.CREATE_EVENT_VERTICAL_PADDING),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.CREATE_EVENT_HORIZONTAL_SPACING)
                ) {
                    data.value.date?.let {
                        InputField(
                            stringResource(R.string.event_date),
                            it,
                            { },
                            modifier = Modifier
                                .weight(1f)
                                .semantics {
                                    contentDescription = Constants.EVENT_DETAILS_DATE
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
                                    contentDescription = Constants.EVENT_DETAILS_TIME
                                },
                            false,
                            true,
                        )
                    }
                }

                Text(
                    stringResource(R.string.participants),
                    color = Ivory,
                    modifier = Modifier.padding(
                        top = Dimensions.CREATE_EVENT_TEXT_PADDING,
                        bottom = Dimensions.CREATE_EVENT_TEXT_PADDING
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
                HorizontalDivider(
                    thickness = Dimensions.CREATE_EVENT_LINE_THICKNESS, color = Ivory
                )
                LaunchedEffect(data.value.eventId) {
                    data.value.eventId?.let {
                        viewmodel.getParticipants()
                    }
                }
                val participants = data.value.participants
                participants.forEach { participant ->
                    ParticipantUI(participant = participant, false, {})
                }
            }
            Column(
            ) {
                ActionButton(stringResource(R.string.leave_event),
                    modifier = Modifier.padding(bottom = Dimensions.ACTION_BUTTON_MEDIUM3),
                    onClick = {
                        viewmodel.onLeave(onSuccess = { navController.popBackStack() },
                            onFailure = { errorMessage ->
                                Log.e("Error", "An error has occurred")
                            })
                    })
            }
        }
    }
}