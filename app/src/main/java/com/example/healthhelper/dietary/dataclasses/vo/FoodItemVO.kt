package com.example.healthhelper.dietary.dataclasses.vo

import com.google.gson.annotations.SerializedName

data class FoodItemVO(
    @SerializedName("diaryID")
    val diaryId:Int,

    @SerializedName("foodID")
    val foodId: Int,

    @SerializedName("mealCategoryID")
    val mealCategoryId: Int? = null,

    @SerializedName("grams")
    val grams: Double,
)