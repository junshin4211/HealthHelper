package com.example.healthhelper.plan.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlin.math.min

@Composable
fun GaugeChart(
    percentage: Float, // 當前百分比 (0 - 100)
    modifier: Modifier = Modifier,
    maxAngle: Float = 240f, // 最大角度
    startAngle: Float = 150f, // 開始角度
    strokeWidth: Float = 40f, // 弧線寬度
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
fun AnimatedGaugeChart(
    modifier: Modifier = Modifier,
    maxPercentage: Float,
    animationStep: Float = 10f, // 每次增加 10%
    animationDuration: Long = 100 // 動畫間隔時間 (毫秒)
) {
    var currentPercentage by remember { mutableStateOf(0f) }

    // 使用動畫來顯示當前百分比
    val animatedPercentage by animateFloatAsState(targetValue = currentPercentage, label = "")

    // 使用 LaunchedEffect 來增加百分比
    LaunchedEffect(Unit) {
        while (currentPercentage < maxPercentage) {
            currentPercentage = (currentPercentage + animationStep).coerceAtMost(maxPercentage)
            delay(animationDuration)
        }
    }

    GaugeChart(
        percentage = animatedPercentage,
        modifier = modifier
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewGaugeChart() {
    MaterialTheme {
        AnimatedGaugeChart(maxPercentage = 100f)
    }
}
