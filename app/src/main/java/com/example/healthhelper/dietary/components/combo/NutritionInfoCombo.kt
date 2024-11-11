package com.example.healthhelper.dietary.components.combo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

    LazyRow(
        modifier = Modifier.size(400.dp,450.dp).verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Center,
    ) {
        item {
            NutritionInfoText(
                nutritionInfoVO = nutritionInfoVO,
                title = title,
                showTitle = showTitle,
            )
        }
        item {
            Spacer(modifier = Modifier.width(20.dp))
        }
        item {
            LazyColumn(
                modifier = Modifier.size(190.dp,200.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                item {
                    NutritionPieChart(nutritionInfoVO)
                }
            }
        }
    }
}