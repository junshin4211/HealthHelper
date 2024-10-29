package com.example.healthhelper.dietary.util.graph.piechart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.math.BigDecimal


@Composable
fun PieChartWithText(
    texts:List<String>,
    entries: List<PieChartEntry>,
    canvasSize: UInt,
    fontSize:UInt,
){
    val currentFontSize by remember { mutableStateOf(fontSize) }
    Column(
        modifier = Modifier
    ){
        PieChart(
            entries = entries,
            size = canvasSize,
        )
        texts.forEachIndexed { index , text ->
            val entry = entries[index]
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "${text}:${entry.percentage.multiply(BigDecimal(100))}%.",
                fontSize = currentFontSize.toInt().sp,
                maxLines = 1,
                overflow = TextOverflow.Visible,
            )
        }
    }
}