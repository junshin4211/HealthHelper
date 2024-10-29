package com.example.healthhelper.person

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.person.model.WeightData
import com.example.healthhelper.person.widget.CustomDateRangePickerDialog
import com.example.healthhelper.person.widget.CustomTabRow
import com.example.healthhelper.person.widget.CustomTopBar
import com.example.healthhelper.person.widget.LineChart
import com.example.healthhelper.person.widget.generateMockPointList
import com.himanshoe.charty.common.toChartDataCollection
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
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
    var showDatePickerRangeDialog by remember { mutableStateOf(false) }
    val today = LocalDate.now()
    val thirtyDaysAgo = today.minusDays(30)
    var selectedDateRange by remember {
        mutableStateOf(
            "${thirtyDaysAgo.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))} ~ ${
                today.format(
                    DateTimeFormatter.ofPattern("yyyy/MM/dd")
                )
            }"
        )
    }
    var selectedTab by remember { mutableStateOf(0) }
    val labels = listOf(
        stringResource(R.string.weight),
        stringResource(R.string.BMI),
        stringResource(R.string.body_fat)
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopBar(
                title = "",
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedDateRange,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = {
                            showDatePickerRangeDialog = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = stringResource(R.string.calendar)
                        )
                    }
                }
                if (showDatePickerRangeDialog) {
                    CustomDateRangePickerDialog(
                        onDismissRequest = { showDatePickerRangeDialog = false },
                        onConfirm = { startDate, endDate ->
                            selectedDateRange = "$startDate ~ $endDate"
                            showDatePickerRangeDialog = false
                        }
                    )
                }
                LazyColumn {
                    item {
                        CustomTabRow(
                            selectedTab = selectedTab,
                            onTabSelected = { newTab -> selectedTab = newTab },
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
            text = stringResource(R.string.body_fat),
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


@Composable
fun WeightList() {
}

@Composable
fun BMIList() {
}

@Composable
fun FatList() {
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun WeightPreview() {
    WeightScreen(rememberNavController())

}