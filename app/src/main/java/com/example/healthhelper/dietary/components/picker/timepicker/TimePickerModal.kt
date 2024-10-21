package com.example.healthhelper.dietary.components.picker.timepicker

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import java.util.Calendar

/**
 * TimePickerExample
 *
 * an util object for TimePickerExample class
 */

object TimePickerModal{
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DialUseStateModal(
        onConfirm: (TimePickerState) -> Unit,
        onDismiss: () -> Unit,
    ) {
        val currentTime = Calendar.getInstance()

        val timePickerState = rememberTimePickerState(
            initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
            initialMinute = currentTime.get(Calendar.MINUTE),
            is24Hour = true,
        )

        Column {
            TimePicker(
                state = timePickerState,
            )
            Button(onClick = onDismiss) {
                Text("Dismiss picker")
            }
            Button(onClick = { onConfirm(timePickerState) }) {
                Text("Confirm selection")
            }
        }
    }
}