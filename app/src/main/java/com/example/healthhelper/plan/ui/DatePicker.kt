package com.example.healthhelper.plan.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import com.example.healthhelper.plan.PlanRepository
import com.example.healthhelper.plan.usecase.PlanUCImpl
import com.example.healthhelper.plan.viewmodel.EditPlanVM
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun CreateDatePicker(
    dateSelected: String?,
    dateStart: Boolean = true,
    EditPlanVM: EditPlanVM
) {
    val repository = PlanRepository
    var date by remember { mutableStateOf(LocalDateTime.now()) }
//    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    var showDatePicker by remember { mutableStateOf(false) }
//    var getdate by remember { mutableStateOf(date.format(formatter)) }
    val dateFormatter = PlanUCImpl()::dateTimeFormat;
    val stringDateFormatter = PlanUCImpl()::stringToTimeStamp;
    var getdate by remember { mutableStateOf(dateFormatter(date)) }

    if (!dateStart) {
        date = LocalDateTime.now().plusWeeks(1L)
        EditPlanVM.updateEndDateTime(stringDateFormatter(date))
    }

    dateformatter(dateSelected ?: DateRange.AWeek.name,
        dateStart,
        onDateselect = {
            date = it
//            getdate = date.format(formatter)
            getdate = dateFormatter(date)
        }
    )

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
                    date = Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC"))
                        .toLocalDateTime()
                }
                showDatePicker = false
//                getdate = date.format(formatter)
                getdate = dateFormatter(date)
                if (dateStart) {
                    EditPlanVM.updateStartDateTime(stringDateFormatter(date))
                } else {
                    EditPlanVM.updateEndDateTime(stringDateFormatter(date))
                }
            },
            onDismiss = {
                showDatePicker = false
            }
        )
    }
}

fun dateformatter(
    dateSelected: String,
    dateStart: Boolean,
    onDateselect: (LocalDateTime) -> Unit
) {
    val today = LocalDateTime.now()
    if (dateStart) {
        onDateselect(today)
    } else {
        when (dateSelected) {
            DateRange.AWeek.name -> onDateselect(today.plusWeeks(1L))

            DateRange.HalfMonth.name -> onDateselect(today.plusWeeks(2L))


            DateRange.AMonth.name -> onDateselect(today.plusMonths(1L))


            DateRange.ThreeMonth.name -> onDateselect(today.plusMonths(3L))


            DateRange.SixMonth.name -> onDateselect(today.plusMonths(6L))
        }
    }
}