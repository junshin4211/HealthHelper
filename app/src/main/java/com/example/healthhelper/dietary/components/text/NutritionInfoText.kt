package com.example.healthhelper.dietary.components.text

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.example.healthhelper.dietary.dataclasses.string.NutritionInfoText1
import com.example.healthhelper.dietary.dataclasses.vo.NutritionInfoVO

@Composable
fun NutritionInfoText(
    nutritionInfoVO: NutritionInfoVO,
    title:@Composable ()->Unit,
    showTitle : Boolean,
){
    val infos = NutritionInfoText1(nutritionInfoVO)
    Column() {
        if(showTitle){
            title()
        }
        infos.forEach {
            Text(
                text = it,
                fontSize = 12.sp,
            )
        }
    }
}