package com.example.healthhelper.dietary.util.graph.piechart

import androidx.compose.ui.graphics.Color
import java.math.BigDecimal

data class PieChartEntry(
    val color: Color,
    val percentage: BigDecimal,
)