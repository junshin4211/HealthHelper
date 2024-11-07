package com.example.healthhelper.plan.ui

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDatePickerDialog(
    onConfirm: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val selectDay = Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneId.of("Asia/Taipei"))
                    .toLocalDate()
                val today = LocalDate.now(ZoneId.of("Asia/Taipei"))
                return selectDay.isEqual(today) || selectDay.isAfter(today) // 允許選擇今天或未來的日期
            }

            override fun isSelectableYear(year: Int): Boolean {
                return year >= 2024
            }

        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    val selectedDateMillis = datePickerState.selectedDateMillis

                    // 如果選取了日期
                    if (selectedDateMillis != null) {
                        val selectedDate = Instant.ofEpochMilli(selectedDateMillis).atZone(ZoneId.of("Asia/Taipei")).toLocalDate()

                        // 獲取當前時間（小時、分鐘、秒）並加到選取日期
                        val currentTime = LocalDateTime.now(ZoneId.of("Asia/Taipei")).toLocalTime()
                        val combinedDateTime = selectedDate.atTime(currentTime)
                        val finalMillis = combinedDateTime.atZone(ZoneId.of("Asia/Taipei")).toInstant().toEpochMilli()

                        onConfirm(finalMillis) // 傳回完整的日期時間戳
                    } else {
                        onConfirm(null)
                    }
                }
            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}