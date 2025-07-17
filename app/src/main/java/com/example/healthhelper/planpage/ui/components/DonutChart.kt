package com.example.healthhelper.planpage.ui.components

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthhelper.planpage.domain.usecase.NutritionGoal
import com.example.healthhelper.planpage.domain.model.ChartData
import kotlin.math.cos
import kotlin.math.sin


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun DonutChart(
    data: List<ChartData>,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 20.dp,
    chartPadding: Dp = 30.dp,
    showPercentageText: Boolean = true
) {
    if (data.isEmpty()) return

    val totalValue = data.sumOf { it.value.toDouble() }.toFloat()
//    if (totalValue == 0f) return

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
                val sweepAngle = if (totalValue == 0f) 0f else (item.value / totalValue) * 360f
                if (sweepAngle > 0f) { // 只繪製有實際角度的部分
                    drawArc(
                        color = item.color,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt) // 使用 Butt 避免圓角重疊
                    )
                }
                startAngle += sweepAngle
            }
        }

        // 2. 放置百分比文字
        if (showPercentageText && totalValue > 0f) {
            var currentStartAngle = -90f

            // 文字放置的半徑（以像素為單位）
            val textRadiusPx =
                chartRadiusPx + with(density) { (strokeWidth / 2 + chartPadding / 2).toPx() }

            data.forEach { item ->
                val percentage = (item.value / totalValue) * 100

                // --- 錯誤修正 2: 在這裡重新計算 sweepAngle ---
                val sweepAngle = (item.value / totalValue) * 360f

                if (percentage > 1.0f) {
                    val midAngle = currentStartAngle + sweepAngle / 2.0
                    val angleInRad = Math.toRadians(midAngle)

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
}

@Composable
fun DonutChart(
    @StringRes planTitle: Int,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 20.dp,
    chartPadding: Dp = 30.dp,
    showPercentageText: Boolean = true,
    colorsForNutrition: List<Color>? = null // 可選參數用於自定義顏色
) {
    // 默認顏色 (Fat, Carb, Protein) - 您可以根據需要調整
    val defaultColors = listOf(
        Color(0xFF00C853), // 脂肪 - 綠色 (沿用之前的)
        Color(0xFF304FFE), // 碳水 - 藍色 (沿用之前的)
        Color(0xFFD50000)  // 蛋白質 - 紅色 (沿用之前的)
    )
    val actualColors = colorsForNutrition ?: defaultColors
    require(actualColors.size >= 3) { "Must provide at least 3 colors for nutrition (Fat, Carb, Protein)." }


    // 使用 LaunchedEffect 或 derivedStateOf 來獲取和轉換數據
    // 這裡使用 remember + LaunchedEffect 模式來處理異步獲取（雖然 getGoals 是同步的，但這是一個好模式）
    var chartDataList by remember { mutableStateOf<List<ChartData>>(emptyList()) }

    // 當 planTitle 改變時，重新獲取營養目標並更新 chartDataList
    LaunchedEffect(planTitle, actualColors) {
        val nutritionRatios = NutritionGoal.getGoals(planTitle) // 獲取 (Fat, Carb, Protein) 比例

        if (nutritionRatios != null) {
            val (fatRatio, carbRatio, proteinRatio) = nutritionRatios
            chartDataList = listOf(
                ChartData(value = fatRatio, color = actualColors[0]),
                ChartData(value = carbRatio, color = actualColors[1]),
                ChartData(value = proteinRatio, color = actualColors[2])
            )
        } else {
            // 如果 planTitle 無效或沒有對應的營養目標，則顯示空數據或默認狀態
            chartDataList = emptyList() // 或者可以是一個表示“無數據”的 ChartData
            // Log.d("DonutChartPlan", "No nutrition goals found for planTitle: $planTitle. Chart will be empty.")
        }
    }

    // 調用您已有的、接收 List<ChartData> 的 DonutChart
    DonutChart(
        data = chartDataList,
        modifier = modifier,
        strokeWidth = strokeWidth,
        chartPadding = chartPadding,
        showPercentageText = showPercentageText
    )
}

