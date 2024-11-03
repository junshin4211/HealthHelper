package com.example.healthhelper.dietary.util.graph.piechart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp

object GraphDrawer {
    fun calculateStartAngles(
        entries: List<PieChartEntry>,
        numberOfEntries: Int,
    ): List<Double> {
        val TAG = "tag_calculateStartAngles"
        var totalPercentage = 0.0
        return entries.map { entry ->
            val startAngle = totalPercentage * 360.0
            totalPercentage += entry.percentage / numberOfEntries
            startAngle
        }
    }

    @Composable
    fun drawPieChart(
        entries: List<PieChartEntry>,
        numberOfEntries: Int,
        size: UInt,
    ) {
        val TAG = "tag_drawPieChart"
        val startAngles = calculateStartAngles(
            entries,
            numberOfEntries,
        )
        Canvas(modifier = Modifier.size(size.toInt().dp)) {
            entries.forEachIndexed { index, entry ->
                drawArc(
                    color = entry.color,
                    startAngle = startAngles[index].toFloat(),
                    sweepAngle = (entry.percentage / numberOfEntries * 360 ).toFloat(),
                    useCenter = true,
                    topLeft = Offset.Zero,
                    size = this.size
                )
            }
        }
    }
}