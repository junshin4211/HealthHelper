package com.example.healthhelper.dietary.dataclasses

import com.google.gson.annotations.SerializedName
import java.sql.Time
import java.sql.Date

data class FoodDiaryDao(
    @SerializedName("diaryID")
    val diaryID: Int,

    @SerializedName("userID")
    val userID: Int,

    @SerializedName("createDate")
    val createDate: Date,

    @SerializedName("createTime")
    val createTime: Time,

    @SerializedName("totalFat")
    val totalFat: Double,

    @SerializedName("totalCarbon")
    val totalCarbon: Double,

    @SerializedName("totalFiber")
    val totalFiber: Double,

    @SerializedName("totalSugar")
    val totalSugar: Double,

    @SerializedName("totalSodium")
    val totalSodium: Double,

    @SerializedName("totalProtein")
    val totalProtein: Double,

    @SerializedName("totalCalories")
    val totalCalories: Double,
)
