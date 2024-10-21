package com.example.healthhelper.dietary.util.graph.piechart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.example.healthhelper.dietary.extension.bigdecimal.sum
import java.math.BigDecimal

fun calculateStartAngles(
    entries: List<PieChartEntry>,
):List<BigDecimal> {
    var totalPercentage = BigDecimal.ZERO
    return entries.map { entry ->
        val startAngle = totalPercentage.multiply(BigDecimal(360))
        totalPercentage = totalPercentage.add(entry.percentage)
        startAngle
    }
}

@Composable
fun PieChart(
    entries: List<PieChartEntry>,
    size:UInt,
){
    assert(entries.map { it.percentage }.sum().toInt() == 1)

    Canvas(modifier = Modifier.size(size.toInt().dp)) {
        val startAngles = calculateStartAngles(entries)
        entries.forEachIndexed { index, entry ->
            drawArc(
                color = entry.color,
                startAngle = startAngles[index].toFloat(),
                sweepAngle = entry.percentage.multiply(BigDecimal(360)).toFloat(),
                useCenter = true,
                topLeft = Offset.Zero,
                size = this.size
            )
        }
    }
}
