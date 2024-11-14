package com.example.healthhelper.person.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.healthhelper.R
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePickerDialog(
    onConfirm: (LocalTime) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = LocalTime.now()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.hour,
        initialMinute = currentTime.minute,
        is24Hour = true
    )

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = null,
        text = {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TimePicker(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        selectorColor = colorResource(R.color.primarycolor),
                        clockDialColor = colorResource(R.color.backgroundcolor),
                        timeSelectorUnselectedContainerColor = colorResource(R.color.backgroundcolor),
                        timeSelectorSelectedContainerColor = colorResource(R.color.footer),
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val selectedTime =
                        LocalTime.of(timePickerState.hour, timePickerState.minute)
                    onConfirm(selectedTime)
                },
                colors = ButtonDefaults.buttonColors(colorResource(R.color.primarycolor))
            ) {
                Text(
                    stringResource(R.string.addTime),
                    color = colorResource(R.color.backgroundcolor)
                )
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(colorResource(R.color.primarycolor))
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = colorResource(R.color.backgroundcolor)
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = colorResource(R.color.backgroundcolor)
    )
}