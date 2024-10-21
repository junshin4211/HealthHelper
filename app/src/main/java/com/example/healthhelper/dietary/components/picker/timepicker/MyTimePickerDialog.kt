package com.example.healthhelper.dietary.components.picker.timepicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar

/**
 * MyTimePickerDialog
 *
 * a class than can pop up a time picker
 */

class MyTimePickerDialog(
    private val buttonText :String,
    private val timeSelectedText: MutableState<String>,
    private val boxModifier: Modifier = Modifier.height(40.dp).fillMaxWidth()
) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun popUp(){
        var showTimePicker by remember { mutableStateOf(false) }
        Box(
            modifier = boxModifier,
            contentAlignment = Alignment.Center
        ) {
            Button(
                modifier = Modifier.fillMaxSize(),
                onClick = { showTimePicker = true }
            ) {
                Text(text = buttonText)
            }
        }

        if (showTimePicker) {
            var selectedTime: TimePickerState? by remember { mutableStateOf(null) }
            TimePickerModal.DialUseStateModal(
                onDismiss = {},
                onConfirm = { time ->
                    selectedTime = time
                },
            )

            if (selectedTime != null) {
                val cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY, selectedTime!!.hour)
                cal.set(Calendar.MINUTE, selectedTime!!.minute)
                cal.isLenient = false

                val formatter = SimpleDateFormat("hh:MM:ss")
                timeSelectedText.value = formatter.format(cal.time)
            }
        }
    }
}