package com.example.healthhelper.dietary.components.combo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.example.healthhelper.dietary.components.piechart.NutritionPieChart
import com.example.healthhelper.dietary.components.text.NutritionInfoText
import com.example.healthhelper.dietary.dataclasses.vo.NutritionInfoVO

@Composable
fun NutritionInfoCombo(
    nutritionInfoVO: NutritionInfoVO,
    title:@Composable ()->Unit,
    showTitle : Boolean,
){
    val TAG = "tag_NutritionInfoCombo"

    Row(
        horizontalArrangement = Arrangement.Center,
    ) {
        NutritionInfoText(
            nutritionInfoVO = nutritionInfoVO,
            title = title,
            showTitle = showTitle,
        )
        NutritionPieChart(nutritionInfoVO)
    }
}