package com.example.healthhelper.dietary.dataclasses.vo

import com.google.gson.annotations.SerializedName

data class NutritionVO(
    @SerializedName("fat")
    val fat:Double,

    @SerializedName("carbon")
    val carbon:Double,

    @SerializedName("protein")
    val protein:Double,

    @SerializedName("fiber")
    val fiber:Double,

    @SerializedName("sugar")
    val sugar:Double,

    @SerializedName("sodium")
    val sodium:Double,

    @SerializedName("calories")
    val calories:Double,
)
