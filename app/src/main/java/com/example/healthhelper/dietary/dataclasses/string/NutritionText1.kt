package com.example.healthhelper.dietary.dataclasses.string

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.example.healthhelper.dietary.dataclasses.vo.NutritionInfoPairVO

@Composable
fun NutritionText1(
    nutritionInfoPairVO: MutableState<NutritionInfoPairVO>,
):String{
    return "${stringResource(nutritionInfoPairVO.value.nameResId)}:${nutritionInfoPairVO.value.amount.value} ${stringResource(nutritionInfoPairVO.value.unitResId)}"
}