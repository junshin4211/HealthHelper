package com.example.healthhelper.dietary.dataclasses.vo

import com.google.gson.annotations.SerializedName

data class FoodVO(
    @SerializedName("foodID")
    val foodID : Int,

    @SerializedName("foodname")
    val foodname: String,

    @SerializedName("gram")
    val gram: Double,

    @SerializedName("fat")
    val fat: Double,

    @SerializedName("carbon")
    val carbon: Double,

    @SerializedName("protein")
    val protein: Double,

    @SerializedName("fiber")
    val fiber: Double,

    @SerializedName("sugar")
    val sugar: Double,

    @SerializedName("sodium")
    val sodium: Double,

    @SerializedName("calories")
    val calories: Double,
)