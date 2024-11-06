package com.example.healthhelper.dietary.components.piechart

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.healthhelper.dietary.dataclasses.vo.NutritionInfoVO
import com.example.healthhelper.dietary.util.graph.piechart.PieChartEntry
import com.example.healthhelper.dietary.util.graph.piechart.PieChartWithText

@Composable
fun NutritionPieChart(
    nutritionInfoVO: NutritionInfoVO,
){
    val TAG = "tag_NutritionPieChart"
    val pieChartEntries = mutableListOf<PieChartEntry>()

    pieChartEntries.add(PieChartEntry(Color.Yellow,  nutritionInfoVO.carbon.value))
    pieChartEntries.add(PieChartEntry(Color.Blue, nutritionInfoVO.protein.value))
    pieChartEntries.add(PieChartEntry(Color.Red, nutritionInfoVO.fat.value))

    val texts = mutableListOf<String>()

    texts.add(nutritionInfoVO.protein.name)
    texts.add(nutritionInfoVO.carbon.name)
    texts.add(nutritionInfoVO.fat.name)

    PieChartWithText(
        texts = texts,
        entries = pieChartEntries,
        numberOfEntries = pieChartEntries.size,
        canvasSize = 70.toUInt(),
        fontSize = 15.toUInt(),
    )
}