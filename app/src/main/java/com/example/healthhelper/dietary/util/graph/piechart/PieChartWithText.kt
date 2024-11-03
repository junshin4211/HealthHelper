package com.example.healthhelper.dietary.util.graph.piechart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PieChartWithText(
    texts:List<String>,
    entries: List<PieChartEntry>,
    numberOfEntries: Int,
    canvasSize: UInt,
    fontSize:UInt,
){
    val currentFontSize by remember { mutableStateOf(fontSize) }
    Column(
        modifier = Modifier.padding(10.dp)
    ){
        GraphDrawer.drawPieChart(
            entries = entries,
            numberOfEntries = numberOfEntries,
            size = canvasSize,
        )
        texts.forEachIndexed { index , text ->
            val entry = entries[index]
            Text(
                text = "${text}:${entry.percentage * 100 / numberOfEntries}%.",
                fontSize = currentFontSize.toInt().sp,
                maxLines = 1,
                overflow = TextOverflow.Visible,
            )
        }
    }
}