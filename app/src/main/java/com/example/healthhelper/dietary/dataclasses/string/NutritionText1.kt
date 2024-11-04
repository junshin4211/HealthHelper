package com.example.healthhelper.dietary.dataclasses.string

import com.example.healthhelper.dietary.dataclasses.vo.NutritionInfoPairVO

fun NutritionText1(
    nutritionInfoPairVO: NutritionInfoPairVO,
):String{
    return "${nutritionInfoPairVO.name}:${nutritionInfoPairVO.value} ${nutritionInfoPairVO.unit}"
}