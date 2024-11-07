package com.example.healthhelper.plan.ui

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.healthhelper.R
import com.example.healthhelper.plan.DateRange
import com.example.healthhelper.plan.usecase.PlanUCImpl
import com.example.healthhelper.plan.viewmodel.EditPlanVM
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
fun CreateDatePicker(
    dateSelected: String?,
    dateStart: Boolean = true,
    EditPlanVM: EditPlanVM
) {
    val tag = "tag_CreateDatePicker"
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormatter = PlanUCImpl()::dateTimeFormat;
    val stringDateFormatter = PlanUCImpl()::stringToTimeStamp;
    var getdate by remember { mutableStateOf("") }
//    val todayByZone = ZonedDateTime.now(ZoneId.of("Asia/Taipei"))
    val todayByZone = LocalDateTime.now(ZoneId.of("Asia/Taipei"))
    LaunchedEffect(dateSelected, dateStart) {
        Log.d(tag, "LaunchedEffect ${ZonedDateTime.now(ZoneId.of("Asia/Taipei"))}")
        // 初始化日期格式並更新到 ViewModel
        rangeFormatter(dateSelected ?: DateRange.AWeek.name, dateStart,todayByZone) { selectedDate ->
            getdate = dateFormatter(selectedDate)
            val timestamp = stringDateFormatter(selectedDate)

            if (dateStart) {
                EditPlanVM.updateStartDateTime(timestamp)
                Log.d(tag, "dateStart ${EditPlanVM.planSetState.value}")
            } else {
                EditPlanVM.updateEndDateTime(timestamp)
                Log.d(tag, "!dateStart ${EditPlanVM.planSetState.value}")
            }
        }
    }

    TextField(
        readOnly = true,
        value = getdate,
        onValueChange = { },
        singleLine = true,
        label = {
            Text(
                if (dateStart) {
                    stringResource(R.string.pickdatestart)
                } else {
                    stringResource(R.string.pickdateend)
                }
            )

        },
        trailingIcon = {
            Icon(
                painter = painterResource(R.drawable.calender),
                contentDescription = "calender",
                modifier = Modifier
                    .scale(2.2f)
                    .clickable { showDatePicker = true }
            )
        },
        modifier = Modifier
            .border(
                width = 2.dp,
                color = colorResource(id = R.color.primarycolor),
                shape = RoundedCornerShape(20.dp)
            ),

        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        )
    )

    if (showDatePicker) {
        CreateDatePickerDialog(
            onConfirm = { utcTimeMillis ->
                utcTimeMillis?.let {
                    val date = Instant.ofEpochMilli(it).atZone(ZoneId.of("Asia/Taipei"))
                        .toLocalDateTime()
//                    val date = Instant.ofEpochMilli(it).atZone(ZoneId.of("Asia/Taipei"))
                    getdate = dateFormatter(date)

                    if (dateStart) {
                        EditPlanVM.updateStartDateTime(stringDateFormatter(date))
                        Log.d(tag,"!dateStart ${EditPlanVM.planSetState.value}")
                    } else {
                        EditPlanVM.updateEndDateTime(stringDateFormatter(date))
                        Log.d(tag,"!dateStart ${EditPlanVM.planSetState.value}")
                    }
                }
                showDatePicker = false
            },
            onDismiss = {
                showDatePicker = false
            }
        )
    }
}

fun rangeFormatter(
    dateSelected: String,
    dateStart: Boolean,
    today: LocalDateTime,
    onDateselect: (LocalDateTime) -> Unit
) {
    if (dateStart) {
        onDateselect(today)
    } else {
        when (dateSelected) {
            DateRange.AWeek.name -> onDateselect(today.plusWeeks(1L))

            DateRange.HalfMonth.name -> onDateselect(today.plusWeeks(2L))

            DateRange.AMonth.name -> onDateselect(today.plusMonths(1L))

            DateRange.ThreeMonth.name -> onDateselect(today.plusMonths(3L))

            DateRange.SixMonth.name -> onDateselect(today.plusMonths(6L))

            else -> onDateselect(today)
        }
    }
}