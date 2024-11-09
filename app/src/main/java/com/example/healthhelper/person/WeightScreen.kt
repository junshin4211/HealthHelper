package com.example.healthhelper.person

import android.util.Log
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.person.model.WeightData
import com.example.healthhelper.person.personVM.WeightViewModel
import com.example.healthhelper.person.widget.CustomDateRangePickerDialog
import com.example.healthhelper.person.widget.CustomTabRow
import com.example.healthhelper.person.widget.CustomTopBar
import com.example.healthhelper.person.widget.LineChart
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.line.model.LineData
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightScreen(
    navController: NavHostController,
    weightViewModel: WeightViewModel
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var showDatePickerRangeDialog by remember { mutableStateOf(false) }

    val dateRange by weightViewModel.dateRangeState.collectAsState()
    val weightData by weightViewModel.weightDataState.collectAsState()
    var selectedDateRange by remember {
        mutableStateOf("${dateRange.startDate} ~ ${dateRange.endDate}")
    }
    var showLineChart by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }

    val labels = listOf(
        stringResource(R.string.weightKg),
        stringResource(R.string.BMI),
        stringResource(R.string.body_fatpercent)
    )


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.myWeight),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigate(PersonScreenEnum.personScreen.name) },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = {
                        navController.navigate(PersonScreenEnum.weightSettingScreen.name)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
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
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                        weightViewModel.setDateRange(startDate, endDate)
                        showDatePickerRangeDialog = false
                    }
                )
            }

            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .shadow(4.dp, shape = RoundedCornerShape(16.dp)),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                                text = stringResource(R.string.BodyDatatrend),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )


                            CustomTabRow(
                                selectedTab = selectedTab,
                                onTabSelected = { newTab -> selectedTab = newTab },
                                textColor = colorResource(R.color.primarycolor),
                                selectedTextColor = Color.White,
                                labels = labels
                            )
                            if (weightData.size < 2) {
                                Text(
                                    stringResource(R.string.noBodyData),
                                    color = Color.Red,
                                    textAlign = TextAlign.Center,
                                    fontSize = 24.sp
                                )
                            } else {
                                showLineChart = true
                                when (selectedTab) {
                                    0 -> LineChartWeightData(
                                        weightData,
                                        valueSelector = { it.weight.toFloat() },
                                        showLineChart
                                    )

                                    1 -> LineChartWeightData(
                                        weightData,
                                        valueSelector = { it.bmi.toFloat() },
                                        showLineChart
                                    )

                                    2 -> LineChartWeightData(
                                        weightData,
                                        valueSelector = { it.bodyFat.toFloat() },
                                        showLineChart
                                    )
                                }
                                Text(stringResource(R.string.month))
                            }
                        }
                    }

                }
                item {
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    Text(
                        stringResource(R.string.historyRecord),
                        modifier = Modifier.fillMaxWidth().padding(start = 12.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    ListItem(
                        { HeaderRow() },
                        colors = ListItemDefaults.colors(containerColor = colorResource(R.color.backgroundcolor)),
                    )
                }
                items(weightData) { item ->
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
            .padding(horizontal = 10.dp, vertical = 16.dp)
            .clickable { navController.navigate("${PersonScreenEnum.weightReviseScreen.name}/${data.recordId}") },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = data.recordDate, modifier = Modifier.weight(1f), fontSize = 18.sp)
        Text(
            text = data.weight.toString(),
            modifier = Modifier.weight(0.5f),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = data.bmi.toString(),
            modifier = Modifier.weight(0.5f),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = data.bodyFat.toString(),
            modifier = Modifier.weight(0.5f),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun LineChartWeightData(
    weightData: List<WeightData>,
    valueSelector: (WeightData) -> Float,
    showLineChart: Boolean = false,
) {
    if (weightData.isNotEmpty() && showLineChart) {
        val sortedWeightData = weightData.sortedBy {
            SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).parse(it.recordDate)
        }
        val dateFormatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val monthFormatter = SimpleDateFormat("MM", Locale.getDefault())

        val chartDataCollection = ChartDataCollection(
            sortedWeightData.map {
                val date = dateFormatter.parse(it.recordDate)
                val month = monthFormatter.format(date ?: "")
                LineData(valueSelector(it), month)
            }
        )
        LineChart(
            dataCollection = chartDataCollection,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
        )
    } else {
        Log.e("LineChart", "No Weightdata")
    }
}


//
//@Preview(showBackground = true)
//@Composable
//fun WeightPreview() {
//    WeightScreen(rememberNavController())
//
//}