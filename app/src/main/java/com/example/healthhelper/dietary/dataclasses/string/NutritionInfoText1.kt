package com.example.healthhelper.dietary.dataclasses.string

import com.example.healthhelper.dietary.dataclasses.vo.NutritionInfoVO

fun NutritionInfoText1(
    nutritionInfoVO: NutritionInfoVO,
):List<String>{
    val members = listOf(
        nutritionInfoVO.fat,
        nutritionInfoVO.carbon,
        nutritionInfoVO.sodium,
        nutritionInfoVO.fiber,
        nutritionInfoVO.sugar,
        nutritionInfoVO.fiber,
        nutritionInfoVO.calories,
    )

    val infos = mutableListOf<String>()

    members.forEach{
        infos.add(NutritionText1(it))
    }

    return  infos.toList()
}