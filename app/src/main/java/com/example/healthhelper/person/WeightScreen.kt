package com.example.healthhelper.person

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthhelper.R


@Composable
fun WeightScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
        Box(
            modifier = Modifier
                .height(270.dp)
                .width(412.dp)
                .background(
                    colorResource(R.color.primarycolor),
                    shape = RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                MeasurementRow("身高", "170.5", "公分")
                Spacer(modifier = Modifier.height(8.dp))
                MeasurementRow("體重", "50.5", "公斤")
                Spacer(modifier = Modifier.height(8.dp))
                MeasurementRow("體脂", "20.6", "百分")
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Handle save */ },
                    modifier = Modifier
                        .width(100.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(stringResource(R.string.save), color = colorResource(R.color.primarycolor), fontSize = 18.sp)
                }
            }
        }

        // Tab Row
        var selectedTab by remember { mutableStateOf(0) }
        TabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 }
            ) {
                Text("體重", modifier = Modifier.padding(vertical = 12.dp))
            }
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 }
            ) {
                Text("BMI", modifier = Modifier.padding(vertical = 12.dp))
            }
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 }
            ) {
                Text("體脂", modifier = Modifier.padding(vertical = 12.dp))
            }
        }

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


@Composable
fun MeasurementRow(label: String, value: String, unit: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = Color.White, fontSize = 24.sp)
        Box(
            modifier = Modifier
                .size(100.dp, 40.dp)
                .background(colorResource(R.color.backgroundcolor), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = value, color = Color.Black, fontSize = 18.sp)
        }
        Text(text = unit, color = Color.White, fontSize = 24.sp)
    }
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