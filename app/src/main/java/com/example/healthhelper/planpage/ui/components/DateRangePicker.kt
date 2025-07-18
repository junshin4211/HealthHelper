package com.example.healthhelper.planpage.ui.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.healthhelper.R
import com.example.healthhelper.planpage.domain.usecase.formatMillisToDateString
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

/** 彈出式日期選擇(範圍)
 * @param initialSelectedStartDateMillis 初始開始日期
 * @param initialSelectedEndDateMillis 初始結束日期
 * @param onConfirm 回傳lambda Pair(開始,結束)*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerDialog(
    onConfirm: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit,
    initialSelectedStartDateMillis: Long? = null,
    initialSelectedEndDateMillis: Long? = null
) {
    val today = LocalDate.now()

    // 計算今天開始時間的 UTC 毫秒數
    val todayMillis = today
        .atStartOfDay(ZoneId.systemDefault()) // 獲取本地時區下，今天的開始時間 (ZonedDateTime)
        .toInstant()
        .toEpochMilli()

    val dateRangePickerState = rememberDateRangePickerState(
        // 設置初始選中的開始和結束日期
        initialSelectedStartDateMillis = initialSelectedStartDateMillis,
        initialSelectedEndDateMillis = initialSelectedEndDateMillis,

        // 初始顯示月份仍然可以設置為今天所在的月份，或者基於 initialSelectedStartDateMillis
        initialDisplayedMonthMillis = initialSelectedStartDateMillis ?: todayMillis,

        // SelectableDates介面用來限制可選擇的日期與年
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val selectedDate = Instant.ofEpochMilli(utcTimeMillis)
                    .atZone(ZoneId.of("UTC")) // DatePicker 傳遞的是 UTC毫秒數
                    .toLocalDate()

                // 檢查日期是否是今天或今天之後
                val isAfterOrEqualToday = !selectedDate.isBefore(today)

                return isAfterOrEqualToday
            }

            override fun isSelectableYear(year: Int): Boolean {
                // 年份也應該大於等於當前年份
                return year >= today.year
            }
        },
        yearRange = DatePickerDefaults.YearRange,
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            Button(onClick = {
                val startDateMillis = dateRangePickerState.selectedStartDateMillis
                val endDateMillis = dateRangePickerState.selectedEndDateMillis

                // 取得當前系統時區時間(時,分,秒)
                val currentTime = LocalTime.now(ZoneId.systemDefault())

                // 取得系統日期午夜時間(00:00:00)轉換成當前日期時間
                val startDate = startDateMillis?.let { midnightMills ->
                    val selectDate = Instant.ofEpochMilli(midnightMills)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                    ZonedDateTime.of(
                        selectDate,
                        currentTime,
                        ZoneId.systemDefault()
                    )
                        .toInstant()
                        .toEpochMilli()
                }

                // 結束日期取當日的(23:59:59)
                val endDate = endDateMillis?.let { midnightMills ->
                    val selectDate = Instant.ofEpochMilli(midnightMills)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                    ZonedDateTime.of(
                        selectDate,
                        LocalTime.of(23, 59, 59),
                        ZoneId.systemDefault()
                    )
                        .toInstant()
                        .toEpochMilli()
                }

                onConfirm(Pair(startDate, endDate))
            }) {
                Text(stringResource(R.string.confirm))
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = Color.Black
        )
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = { Text(stringResource(R.string.pickDateRange)) },
            showModeToggle = true,
            headline = {
                val startDateMillis = dateRangePickerState.selectedStartDateMillis
                val endDateMillis = dateRangePickerState.selectedEndDateMillis

                // 轉成特定格式顯示
                val startDateString = formatMillisToDateString(startDateMillis)
                val endDateString = formatMillisToDateString(endDateMillis)

                // Dialog標題
                val headlineText = when {
                    startDateMillis != null && endDateMillis != null -> {
                        "$startDateString - $endDateString"
                    }

                    startDateMillis != null -> {
                        startDateString
                    }

                    else -> {
                        "請選擇日期範圍"
                    }
                }
                Text(
                    text = headlineText,
                    style = MaterialTheme.typography.headlineSmall, // 應用 Material 樣式
                    color = Color.White // 設置文本顏色
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(550.dp)
                .padding(12.dp),
            colors = DatePickerDefaults.colors( // <--- 自定義 DateRangePicker 的顏色
                containerColor = Color.Black, // 日期選擇器主要內容的背景色
                titleContentColor = Color.White, // 標題文本顏色 (如果 title composable 未指定顏色)
                headlineContentColor = Color.White, // 頭部選定日期範圍文本的顏色
                weekdayContentColor = Color.White, // 星期標籤 (一, 二...) 的文本顏色
                dayContentColor = Color.White, // 日期數字的默認文本顏色
                disabledDayContentColor = Color.Gray, // 禁用日期的文本顏色
                selectedDayContentColor = Color.Black, // 選中日期的文本顏色 (通常與選中容器顏色對比)
                disabledSelectedDayContentColor = Color.LightGray, // 禁用且選中日期的文本顏色
                selectedDayContainerColor = MaterialTheme.colorScheme.primary, // 選中日期的背景圓圈顏色
                disabledSelectedDayContainerColor = Color.Gray,
                todayContentColor = MaterialTheme.colorScheme.primary, // “今天”日期指示器的顏色 (如果與選中顏色不同)
                todayDateBorderColor = MaterialTheme.colorScheme.primary, // “今天”日期邊框的顏色
                dayInSelectionRangeContentColor = Color.Black, // 範圍內日期的文本顏色
            )
        )
    }
}