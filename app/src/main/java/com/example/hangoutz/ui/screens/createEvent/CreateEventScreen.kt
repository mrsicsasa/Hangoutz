package com.example.hangoutz.ui.screens.createEvent

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.ActionButton
import com.example.hangoutz.ui.components.DatePickerModal
import com.example.hangoutz.ui.components.InputField
import com.example.hangoutz.ui.components.InputFieldWithIcon
import com.example.hangoutz.ui.components.TimePickerModal
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.ui.theme.TopBarBackgroundColor
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    viewmodel: CreateEventViewModel = hiltViewModel()
) {
    val data = viewmodel.uiState.collectAsState()

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
                .padding(
                    top = innerPadding.calculateTopPadding() + Dimensions.EVENTDETAILS_TOP_PADDING,
                    start = Dimensions.ACTION_BUTTON_MEDIUM2,
                    end = Dimensions.ACTION_BUTTON_MEDIUM2
                )
                .verticalScroll(rememberScrollState())

        ) {
            InputField(
                stringResource(R.string.event_title),
                data.value.title,
                { viewmodel.onTitleChange(it) },
                modifier = Modifier.semantics {
                    contentDescription = Constants.CREATE_EVENT_TITLE_FIELD
                },
                true
            )

            InputField( stringResource(R.string.event_desc),
                data.value.description,
                { viewmodel.onDescriptionChange(it) },
                modifier = Modifier.semantics {
                    contentDescription = Constants.CREATE_EVENT_DESC_FIELD
                },
                true
            )


            InputField( stringResource(R.string.event_city),
                data.value.city,
                { viewmodel.onCityChange(it) },
                modifier = Modifier.semantics {
                    contentDescription = Constants.CREATE_EVENT_CITY_FIELD
                },
                true
            )


            InputField( stringResource(R.string.event_street),
                data.value.street,
                { viewmodel.onStreetChange(it) },
                modifier = Modifier.semantics {
                    contentDescription = Constants.CREATE_EVENT_STREET_FIELD
                },
                true
            )


            InputField( stringResource(R.string.event_place),
                data.value.place,
                { viewmodel.onPlaceChange(it) },
                modifier = Modifier.semantics {
                    contentDescription = Constants.CREATE_EVENT_PLACE_FIELD
                },
                true
            )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(35.dp)
        ) {

            InputFieldWithIcon( stringResource(R.string.event_date),
                data.value.date,
                { viewmodel.onDateChange(it) },
                modifier = Modifier
                    .weight(1f)
                    .semantics {
                        contentDescription = Constants.CREATE_EVENT_DATE_FIELD
                    },
                R.drawable.calendaricon,
                true,
                true,
                {viewmodel.setShowDatePicker()}

            )

            InputFieldWithIcon( stringResource(R.string.event_time),
                data.value.time,
                { viewmodel.onTimeChange(it) },
                modifier = Modifier
                    .weight(1f)
                    .semantics {
                        contentDescription = Constants.CREATE_EVENT_TIME_FIELD
                    },
                R.drawable.clockicon,
                true,
                true,
                {viewmodel.setShowTimePicker()})

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                stringResource(R.string.event_participants),
                color = Ivory,
                modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Image(painter = painterResource(id = R.drawable.addevent),
                contentDescription = "",
                modifier = Modifier.clickable { }
                    .semantics { contentDescription = Constants.CREATE_EVENT_ADD_PARTICIPANTS_BUTTON }


            )
        }



        HorizontalDivider(thickness = 2.dp, color = Ivory)


        //TODO put participants here, use participantUI component


        ActionButton(stringResource(R.string.event_create),
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(top = 15.dp, bottom = 15.dp)
                .semantics { contentDescription= Constants.CREATE_EVENT_ADD_CREATE_BUTTON},
            onClick = {
                viewmodel.createEvent()
            }
        )



            if(data.value.showDatePicker){
                DatePickerModal(
                    onDateSelected = { date ->
                        date?.let {
                            viewmodel.onDatePicked(date)
                        }
                    },
                    onDismiss = { viewmodel.setShowDatePicker() })

            }

            if (data.value.showTimePicker) {
                TimePickerModal(
                    onConfirm = { time ->
                        viewmodel.onTimePicked(time)
                                viewmodel.setShowTimePicker()
                    },
                    onDismiss = { viewmodel.setShowTimePicker() }
                )
            }
    }
}
}
