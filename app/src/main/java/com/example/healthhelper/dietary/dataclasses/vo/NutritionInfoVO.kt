package com.example.healthhelper.dietary.dataclasses.vo

import androidx.compose.runtime.MutableState

data class NutritionInfoVO(
    val fat:MutableState<NutritionInfoPairVO>,

    val carbon:MutableState<NutritionInfoPairVO>,

    val protein:MutableState<NutritionInfoPairVO>,

    val fiber:MutableState<NutritionInfoPairVO>,

    val sugar:MutableState<NutritionInfoPairVO>,

    val sodium:MutableState<NutritionInfoPairVO>,

    val calories:MutableState<NutritionInfoPairVO>,
)
