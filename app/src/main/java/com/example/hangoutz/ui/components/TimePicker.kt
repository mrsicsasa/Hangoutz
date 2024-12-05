package com.example.hangoutz.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.hangoutz.R
import com.example.hangoutz.utils.Dimensions
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerModal(
    onConfirm: (Long) -> Unit,
    onDismiss: () -> Unit,
) {

    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.clip(RoundedCornerShape(Dimensions.TIME_PICKER_CORNERS))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.White)
                .padding(
                    Dimensions.ACTION_BUTTON_MEDIUM2
                )
        ) {
            TimeInput(
                state = timePickerState
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimensions.ACTION_BUTTON_SMALL2)
            ) {
                Button(onClick = {
                    val selectedCalendar = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                        set(Calendar.MINUTE, timePickerState.minute)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                    val selectedTimeInMillis = selectedCalendar.timeInMillis
                    onConfirm(selectedTimeInMillis)
                }) {
                    Text(stringResource(R.string.confirm_time_picker))
                }
                Button(onClick = onDismiss) {
                    Text(stringResource(R.string.dismiss_time_picker))
                }
            }
        }
    }
}