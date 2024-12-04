package com.example.hangoutz.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.example.hangoutz.R
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
    Dialog(onDismissRequest = onDismiss) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            TimeInput(
                state = timePickerState,
                modifier = Modifier.align(Alignment.CenterHorizontally)

            )
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

