package com.example.healthhelper.plan.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.min

@Composable
fun GaugeChart(
    percentage: Float, // 當前百分比 (0 - 100)
    modifier: Modifier = Modifier,
    maxAngle: Float = 240f, // 最大角度
    startAngle: Float = 150f, // 開始角度
    strokeWidth: Float = 20f, // 弧線寬度
) {
    Canvas(modifier = modifier) {
        val canvasSize = size
        val radius = min(canvasSize.width, canvasSize.height) / 2
        // 中心點
        val center = Offset(x = canvasSize.width / 2, y = canvasSize.height / 2)

        // 背景弧線
        drawArc(
            color = Color.LightGray,
            startAngle = startAngle,
            sweepAngle = maxAngle,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth)
        )

        // 進度弧線
        val sweepAngle = (percentage / 100f) * maxAngle
        val gradient = Brush.sweepGradient(
            0f to Color(0xFFFFA726), // 淺橘色
            0.5f to Color(0xFFFF7043), // 橘紅色
            1f to Color(0xFFFF9800) // 深橘色
        )
        drawArc(
            brush = gradient,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}

@Composable
fun GaugeChartDemo() {
    var value by remember { mutableStateOf(50f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GaugeChart(
            percentage = value,
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Slider(
            value = value,
            onValueChange = { value = it },
            valueRange = 0f..100f
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewGaugeChart() {
    MaterialTheme {
        GaugeChartDemo()
    }
}
