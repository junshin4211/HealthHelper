package com.example.healthhelper.person.widget

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.healthhelper.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun CustomDateRangePickerDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (String, String) -> Unit,
) {
    val today = LocalDate.now()
    val oneMonthAgo = today.minusMonths(1)

    val initialStartDateMillis =
        oneMonthAgo.atStartOfDay(ZoneId.of("UTC")).toInstant().toEpochMilli()
    val initialEndDateMillis = today.atStartOfDay(ZoneId.of("UTC")).toInstant().toEpochMilli()

    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = initialStartDateMillis,
        initialSelectedEndDateMillis = initialEndDateMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val selectedDate = Instant.ofEpochMilli(utcTimeMillis)
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDate()
                return !selectedDate.isAfter(today)
            }
        }
    )
    val startDate = dateRangePickerState.selectedStartDateMillis?.let {
        Instant.ofEpochMilli(it)
            .atZone(ZoneId.of("UTC"))
            .toLocalDate()
            .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
    } ?: stringResource(R.string.noChoose)
    val endDate = dateRangePickerState.selectedEndDateMillis?.let {
        Instant.ofEpochMilli(it)
            .atZone(ZoneId.of("UTC"))
            .toLocalDate()
            .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
    } ?: stringResource(R.string.noChoose)

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(startDate, endDate)
                },
                colors = ButtonDefaults.buttonColors(colorResource(R.color.backgroundcolor))
            ) {
                Text(text = stringResource(R.string.confirm), color = colorResource(R.color.primarycolor))
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(colorResource(R.color.backgroundcolor))
            ) {
                Text(text = stringResource(R.string.cancel), color = colorResource(R.color.primarycolor))
            }
        },
        text = {
            DateRangePicker(
                state = dateRangePickerState,
                showModeToggle = false,
                title = { },
                headline = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "$startDate ~ $endDate",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                },
                colors = DatePickerDefaults.colors(
                    containerColor = colorResource(R.color.primarycolor),
                    weekdayContentColor = Color.White,
                    subheadContentColor = Color.White,
                    yearContentColor = Color.White,
                    currentYearContentColor = Color.White,
                    selectedYearContainerColor = Color.White.copy(alpha = 0.3f),
                    selectedYearContentColor = Color.White,
                    dayContentColor = Color.White,
                    selectedDayContainerColor = Color.White.copy(alpha = 0.3f),
                    selectedDayContentColor = Color.White,
                    todayContentColor = Color.White,
                    todayDateBorderColor = Color.White
                )
            )
        },
        shape = RoundedCornerShape(15.dp),
        containerColor = colorResource(R.color.primarycolor),
        modifier = Modifier.width(400.dp)
    )
}