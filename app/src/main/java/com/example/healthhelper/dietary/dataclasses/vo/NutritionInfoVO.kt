package com.example.healthhelper.dietary.dataclasses.vo

import com.google.gson.annotations.SerializedName

data class NutritionInfoVO(
    @SerializedName("fat")
    val fat:NutritionInfoPairVO,

    @SerializedName("carbon")
    val carbon:NutritionInfoPairVO,

    @SerializedName("protein")
    val protein:NutritionInfoPairVO,

    @SerializedName("fiber")
    val fiber:NutritionInfoPairVO,

    @SerializedName("sugar")
    val sugar:NutritionInfoPairVO,

    @SerializedName("sodium")
    val sodium:NutritionInfoPairVO,

    @SerializedName("calories")
    val calories:NutritionInfoPairVO,
)
