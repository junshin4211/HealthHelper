package com.example.healthhelper.dietary.dataclasses.string

import androidx.compose.runtime.Composable
import com.example.healthhelper.dietary.dataclasses.vo.NutritionInfoVO

@Composable
fun NutritionInfoText1(
    nutritionInfoVO: NutritionInfoVO,
):List<String>{
    val members = listOf(
        nutritionInfoVO.fat,
        nutritionInfoVO.carbon,
        nutritionInfoVO.protein,
        nutritionInfoVO.sodium,
        nutritionInfoVO.fiber,
        nutritionInfoVO.sugar,
        nutritionInfoVO.calories,
    )

    val infos = mutableListOf<String>()

    members.forEach{
        infos.add(NutritionText1(it))
    }

    return  infos.toList()
}