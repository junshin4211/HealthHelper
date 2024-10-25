package com.example.healthhelper.person

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.person.model.WeightData
import com.example.healthhelper.person.widget.CustomTabRow
import com.example.healthhelper.person.widget.CustomTopBar
import com.example.healthhelper.person.widget.LineChart
import com.example.healthhelper.person.widget.generateMockPointList
import com.himanshoe.charty.common.toChartDataCollection
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightScreen(
    navController: NavHostController,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val data = listOf(
        WeightData("10/09", 50.5f, 25.5f, 33.4f),
        WeightData("10/01", 70.3f, 30.3f, 20.5f),
        WeightData("09/27", 99.9f, 30.5f, 36.1f),
        WeightData("09/20", 48.5f, 22.6f, 27.3f),
        WeightData("09/20", 48.5f, 22.6f, 27.3f),
        WeightData("09/20", 48.5f, 22.6f, 27.3f),
        WeightData("09/20", 48.5f, 22.6f, 27.3f),
    )
    var selectedTab by remember { mutableStateOf(0) }
    val tabColors = listOf(
        colorResource(R.color.primarycolor),
        colorResource(R.color.primarycolor),
        colorResource(R.color.primarycolor)
    )
    val labels = listOf(
        stringResource(R.string.weight),
        stringResource(R.string.BMI),
        stringResource(R.string.fat)
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = {
                        navController.navigate(PersonScreenEnum.weightSettingScreen.name)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(R.string.weightSetting),
                            modifier = Modifier.size(40.dp),
                            tint = colorResource(R.color.primarycolor)
                        )
                    }
                }
            )
        }
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
                .background(colorResource(R.color.backgroundcolor))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomDateRangePicker()
                LazyColumn {
                    item {
                        CustomTabRow(
                            selectedTab = selectedTab,
                            onTabSelected = { newTab -> selectedTab = newTab },
                            tabColors = tabColors,
                            textColor = Color.Black,
                            selectedTextColor = Color.White,
                            labels = labels
                        )
                        when (selectedTab) {
                            0 -> WeightList()
                            1 -> BMIList()
                            2 -> FatList()
                        }
                        LineChart(
                            dataCollection = generateMockPointList().toChartDataCollection(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                stringResource(R.string.historyRecord),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline
                            )
                        }
                        ListItem(
                            { HeaderRow() },
                            colors = ListItemDefaults.colors(containerColor = colorResource(R.color.backgroundcolor)),
                        )
                    }
                    items(data) { item ->
                        WeightDataRow(item, navController = navController)
                        HorizontalDivider(
                            color = colorResource(R.color.primarycolor),
                            thickness = 1.dp
                        )
                    }
                }
            }


        }

    }
}

@Composable
fun HeaderRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.weight),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.5f),
            fontSize = 18.sp
        )
        Text(
            text = stringResource(R.string.BMI),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.5f),
            fontSize = 18.sp
        )
        Text(
            text = stringResource(R.string.fat),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.5f),
            fontSize = 18.sp
        )
    }
}

@Composable
fun WeightDataRow(data: WeightData, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .clickable { navController.navigate(PersonScreenEnum.weightReviseScreen.name) },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = data.date, modifier = Modifier.weight(1f), fontSize = 18.sp)
        Text(text = data.weight.toString(), modifier = Modifier.weight(0.5f), fontSize = 18.sp)
        Text(text = data.bmi.toString(), modifier = Modifier.weight(0.5f), fontSize = 18.sp)
        Text(text = data.bodyFat.toString(), modifier = Modifier.weight(0.5f), fontSize = 18.sp)
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
            .height(240.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            showModeToggle = false,
            title = { },
            headline = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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


@Preview(showBackground = true)
@Composable
fun WeightPreview() {
    WeightScreen(rememberNavController())

}