package com.example.healthhelper.planpage.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OverallProgressSection(progress: Float) {
    val progressDegrees = progress * 180f
    val progressColor = when {
        progress < 0.5f -> Color.Red // 進度小於50%時為紅色
        progress < 0.8f -> Color.Yellow // 進度介於50%到80%時為黃色
        else -> Color.Green // 進度大於等於80%時為綠色
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(180.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 20.dp.toPx()
            // 背景圓環
            drawArc(
                color = Color.LightGray.copy(alpha = 0.3f),
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            // 進度圓環 (從顶部開始, 順時針)
            drawArc(
                color = progressColor,
                startAngle = 180f, // 從 9 點鐘方向開始
                sweepAngle = progressDegrees,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Text(
            text = "${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
    }

}

@Preview(showBackground = true)
@Composable
fun OverallProgressSectionPreview() {
    OverallProgressSection(progress = 0.75f)
}