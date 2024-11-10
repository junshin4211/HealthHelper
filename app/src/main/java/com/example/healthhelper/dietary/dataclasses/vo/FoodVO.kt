package com.example.healthhelper.dietary.dataclasses.vo

import com.google.gson.annotations.SerializedName

data class FoodVO(
    @SerializedName("foodId")
    var foodId : Int = -1,

    @SerializedName("foodName")
    var foodName: String = "",

    @SerializedName("gram")
    val gram: Double = 0.0,

    @SerializedName("fat")
    val fat: Double = 0.0,

    @SerializedName("carbon")
    val carbon: Double = 0.0,

    @SerializedName("protein")
    val protein: Double = 0.0,

    @SerializedName("fiber")
    val fiber: Double = 0.0,

    @SerializedName("sugar")
    val sugar: Double = 0.0,

    @SerializedName("sodium")
    val sodium: Double = 0.0,

    @SerializedName("calories")
    val calories: Double = 0.0,
)