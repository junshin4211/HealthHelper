package com.example.healthhelper.dietary.components.combo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        Spacer(modifier = Modifier.width(20.dp))
        NutritionPieChart(nutritionInfoVO)
    }
}