package com.example.healthhelper.signuplogin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.time.Instant
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onConfirm: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val today = Instant.now().truncatedTo(ChronoUnit.DAYS).toEpochMilli()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = today // 設置初始日期為今天
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(datePickerState.selectedDateMillis)
                    onDismiss() // 加入這行來確保點擊確認後關閉對話框
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (datePickerState.selectedDateMillis != null &&
                        datePickerState.selectedDateMillis!! <= today) {
                        Color(0xFFD75813)
                    } else {
                        Color.Gray
                    }
                ),
                enabled = datePickerState.selectedDateMillis != null &&
                        datePickerState.selectedDateMillis!! <= today
            ) {
                Text(
                    text = "確認",
                    color = Color.White
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFFEED)
                )
            ) {
                Text(
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
                    // 保持其他顏色設定
                    containerColor = Color(0xFFEFE9F4),
                    titleContentColor = Color(0xFFD75813),
                    headlineContentColor = Color(0xFFD75813),
                    weekdayContentColor = Color(0xFF555555),
                    subheadContentColor = Color(0xFF555555),
                    yearContentColor = Color(0xFF555555),
                    currentYearContentColor = Color(0xFFD75813),
                    dayContentColor = Color(0xFF555555),
                )
            )
        }
    }
}