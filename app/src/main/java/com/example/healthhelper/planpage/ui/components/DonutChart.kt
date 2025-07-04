package com.example.healthhelper.planpage.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthhelper.planpage.ui.ChartData
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun DonutChart(
    data: List<ChartData>,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 20.dp,
    chartPadding: Dp = 30.dp
) {
    val totalValue = data.sumOf { it.value.toDouble() }.toFloat()
    if (totalValue == 0f) return

    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val fullDiameter = minOf(maxWidth, maxHeight)
        val chartDiameter = fullDiameter - (chartPadding * 2)
        val chartRadiusPx = with(density) { (chartDiameter / 2).toPx() }

        // 1. 繪製圓環的 Canvas
        Canvas(
            modifier = Modifier.size(chartDiameter)
        ) {
            var startAngle = -90f
            data.forEach { item ->
                val sweepAngle = (item.value / totalValue) * 360f
                drawArc(
                    color = item.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt)
                )
                startAngle += sweepAngle
            }
        }

        // 2. 放置百分比文字
        var currentStartAngle = -90f

        // 文字放置的半徑（以像素為單位）
        val textRadiusPx = chartRadiusPx + with(density) { (strokeWidth / 2 + chartPadding / 2).toPx() }

        data.forEach { item ->
            val percentage = (item.value / totalValue) * 100

            // --- 錯誤修正 2: 在這裡重新計算 sweepAngle ---
            val sweepAngle = (item.value / totalValue) * 360f

            if (percentage > 5) {
                val midAngle = currentStartAngle + sweepAngle / 2.0
                val angleInRad = Math.toRadians(midAngle)

                // --- 錯誤修正 1: 先計算像素值，再轉換回 Dp ---
                // 所有計算都在像素（Px）層面進行，最後才轉為 Dp 給 offset
                val textCenterX_px = (textRadiusPx * cos(angleInRad)).toFloat()
                val textCenterY_px = (textRadiusPx * sin(angleInRad)).toFloat()

                // 將計算好的像素偏移量轉換回 Dp
                val offsetX = with(density) { textCenterX_px.toDp() }
                val offsetY = with(density) { textCenterY_px.toDp() }

                Text(
                    text = "${percentage.toInt()}%",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.offset(x = offsetX, y = offsetY)
                )
            }
            // 更新下一個文字的角度
            currentStartAngle += sweepAngle
        }
    }
}