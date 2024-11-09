package com.example.healthhelper.dietary.dataclasses.vo

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState

data class NutritionInfoPairVO(
    @StringRes val nameResId:Int,
    var amount:MutableState<Double>,
    @StringRes val unitResId:Int,
)
