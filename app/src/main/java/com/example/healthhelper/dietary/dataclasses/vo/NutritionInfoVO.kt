package com.example.healthhelper.dietary.dataclasses.vo

import androidx.compose.runtime.MutableState
import com.google.gson.annotations.SerializedName

data class NutritionInfoVO(
    @SerializedName("fat")
    val fat:MutableState<NutritionInfoPairVO>,

    @SerializedName("carbon")
    val carbon:MutableState<NutritionInfoPairVO>,

    @SerializedName("protein")
    val protein:MutableState<NutritionInfoPairVO>,

    @SerializedName("fiber")
    val fiber:MutableState<NutritionInfoPairVO>,

    @SerializedName("sugar")
    val sugar:MutableState<NutritionInfoPairVO>,

    @SerializedName("sodium")
    val sodium:MutableState<NutritionInfoPairVO>,

    @SerializedName("calories")
    val calories:MutableState<NutritionInfoPairVO>,
)
