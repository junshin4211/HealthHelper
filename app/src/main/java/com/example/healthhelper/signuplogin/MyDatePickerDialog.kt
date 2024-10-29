package com.example.healthhelper.signuplogin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.time.Instant
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onConfirm: (message: Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val today = Instant.now().truncatedTo(ChronoUnit.DAYS).toEpochMilli()
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            // 如果所選日期小於或等於今天，啟用「確認」按鈕
            androidx.compose.material3.Button(
                onClick = {
                    onConfirm(datePickerState.selectedDateMillis)
                },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = if (datePickerState.selectedDateMillis != null && datePickerState.selectedDateMillis!! <= today) {
                        Color(0xFFD75813)
                    } else {
                        Color.Gray // 禁用按鈕
                    }
                ),
                enabled = datePickerState.selectedDateMillis != null && datePickerState.selectedDateMillis!! <= today // 禁用超過今天的日期
            ) {
                androidx.compose.material3.Text(
                    text = "確認",
                    color = Color.White
                )
            }
        },
        dismissButton = {
            androidx.compose.material3.Button(
                onClick = onDismiss,
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFFEED)
                )
            ) {
                androidx.compose.material3.Text(
                    text = "取消",
                    color = Color(0xFFD75813)
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEFE9F4))
        ) {
            DatePicker(
                state = datePickerState,
                modifier = Modifier.background(Color(0xFFEFE9F4)),
                colors = DatePickerDefaults.colors(
                    selectedDayContentColor = Color.White,
                    selectedDayContainerColor = Color(0xFFF19204),
                    todayContentColor = Color(0xFFFFA500),
                    todayDateBorderColor = Color(0xFFFFA500),

                    )
            )
        }
    }
}