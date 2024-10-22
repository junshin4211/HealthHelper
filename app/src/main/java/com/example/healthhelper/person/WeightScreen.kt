package com.example.healthhelper.person

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthhelper.R
import com.example.healthhelper.person.widget.CustomTabRow
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofLocalizedDate
import java.time.format.FormatStyle


@Composable
fun WeightScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Handle back navigation */ }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = "Back"
                )
            }
            IconButton(onClick = { /* Handle calendar */ }) {
                Icon(Icons.Default.DateRange, contentDescription = "Calendar")
            }
        }
        CustomDateRangePicker()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var selectedTab by remember { mutableStateOf(0) }
            val tabColors = listOf(
                colorResource(R.color.primarycolor),
                colorResource(R.color.primarycolor),
                colorResource(R.color.primarycolor)
            )
            val textColor = Color.Black
            val selectedTextColor = Color.White
            val labels = listOf(
                stringResource(R.string.weight),
                stringResource(R.string.BMI),
                stringResource(R.string.fat)
            )

            CustomTabRow(
                selectedTab = selectedTab,
                onTabSelected = { newTab -> selectedTab = newTab },
                tabColors = tabColors,
                textColor = textColor,
                selectedTextColor = selectedTextColor,
                labels = labels
            )
            when (selectedTab) {
                0 -> WeightList()
                1 -> BMIList()
                2 -> FatList()
            }
// 待修正
//         Chart Section
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .padding(vertical = 8.dp)
//        ) {
//            val dataPoints = listOf(
//                LineData(54.5f,"9/20"),
//                LineData( 55.0f,"9/27"),
//                LineData(52.5f,"10/1"),
//                LineData(50.5f,"10/9")
//            )
//
//            val dataCollection = ChartDataCollection(
//                data = dataPoints
//            )
//            val axisConfig = AxisConfig(
//                showAxes = true,
//                showGridLines = true,
//                showGridLabel = true,
//                axisStroke = 1f,
//                minLabelCount = 4,
//                axisColor = Color(0xFF2196F3),
//                gridColor = Color.LightGray.copy(alpha = 0.5f)
//            )
//
//            LineChart(
//                dataCollection = dataCollection,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                padding = 16.dp,
//                axisConfig = axisConfig,
//                radiusScale = 0.02f,
//                lineConfig = LineConfig(
//                    hasSmoothCurve = true,
//                    hasDotMarker = true,
//                    strokeSize = 2f
//                ),
//                chartColors = LineChartColors(
//                    lineColor = listOf(Color(0xFF2196F3)),
//                    dotColor = listOf(Color(0xFF2196F3)),
//                    backgroundColors = listOf(Color.Transparent)
//                )
//            )
//        }

            Text(
                stringResource(R.string.historyRecord),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            LazyColumn {
                items(historyItems) { item ->
                    HistoryItem(item)
                }
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDateRangePicker() {
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
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(15.dp),
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            showModeToggle = false,
            title = { },
            headline = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val startDate = dateRangePickerState.selectedStartDateMillis?.let {
                        Instant.ofEpochMilli(it)
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDate()
                            .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                    } ?: "未選擇"
                    val endDate = dateRangePickerState.selectedEndDateMillis?.let {
                        Instant.ofEpochMilli(it)
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDate()
                            .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                    } ?: "未選擇"
                    Text(
                        text = "$startDate ~ $endDate",
                        color = Color.White
                    )

                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = colorResource(R.color.primarycolor),
//                weekdayContentColor = Color.White,
//                subheadContentColor = Color.White,
//                yearContentColor = Color.White,
//                currentYearContentColor = Color.White,
//                selectedYearContainerColor = Color.White.copy(alpha = 0.3f),
//                selectedYearContentColor = Color.White,
//                dayContentColor = Color.White,
//                selectedDayContainerColor = Color.White.copy(alpha = 0.3f),
//                selectedDayContentColor = Color.White,
//                todayContentColor = Color.White,
//                todayDateBorderColor = Color.White
            )
        )
    }
}


@Composable
fun WeightList() {
}

@Composable
fun BMIList() {
}

@Composable
fun FatList() {
}

@Composable
fun HistoryItem(item: HistoryData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(item.date)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "${item.weight} 公斤",
                modifier = Modifier.padding(end = 8.dp)
            )
            Icon(
                if (item.trend == Trend.UP) Icons.Default.KeyboardArrowUp
                else Icons.Default.KeyboardArrowDown,
                contentDescription = "Trend",
                tint = if (item.trend == Trend.UP) Color.Red else Color.Green
            )
        }
    }
}


data class HistoryData(
    val date: String,
    val weight: Double,
    val trend: Trend,
)

enum class Trend {
    UP, DOWN
}

val historyItems = listOf(
    HistoryData("10/09", 50.5, Trend.DOWN),
    HistoryData("10/01", 52.5, Trend.DOWN),
    HistoryData("09/27", 55.0, Trend.UP),
    HistoryData("09/20", 54.5, Trend.DOWN)
)

@Preview(showBackground = true)
@Composable
fun WeightPreview() {
    WeightScreen()

}