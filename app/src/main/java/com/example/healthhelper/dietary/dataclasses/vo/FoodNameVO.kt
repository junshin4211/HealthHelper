package com.example.healthhelper.dietary.dataclasses.vo

import com.google.gson.annotations.SerializedName

data class FoodNameVO(
    @SerializedName("foodName")
    val foodName:String,
)