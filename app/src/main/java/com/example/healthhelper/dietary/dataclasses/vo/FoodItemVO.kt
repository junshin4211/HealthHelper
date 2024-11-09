package com.example.healthhelper.dietary.dataclasses.vo

import com.google.gson.annotations.SerializedName

data class FoodItemVO(
    @SerializedName("diaryID")
    var diaryID:Int,

    @SerializedName("foodID")
    var foodID: Int,

    @SerializedName("mealCategoryID")
    var mealCategoryID: Int = 0,

    @SerializedName("grams")
    var grams: Double,
)