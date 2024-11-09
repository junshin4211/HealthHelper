package com.example.healthhelper.dietary.dataclasses.vo

import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Time

data class FoodDiaryVO(
    @SerializedName("diaryID")
    var diaryID: Int = 2,

    @SerializedName("userID")
    var userID: Int = 2,

    @SerializedName("createDate")
    var createDate: Date = Date(0),

    @SerializedName("createTime")
    val createTime: Time = Time(0),

    @SerializedName("totalFat")
    val totalFat: Double = -1.0,

    @SerializedName("totalCarbon")
    val totalCarbon: Double = -1.0,

    @SerializedName("totalFiber")
    val totalFiber: Double = -1.0,

    @SerializedName("totalSugar")
    val totalSugar: Double = -1.0,

    @SerializedName("totalSodium")
    val totalSodium: Double = -1.0,

    @SerializedName("totalProtein")
    val totalProtein: Double = -1.0,

    @SerializedName("totalCalories")
    val totalCalories: Double = -1.0,
)
