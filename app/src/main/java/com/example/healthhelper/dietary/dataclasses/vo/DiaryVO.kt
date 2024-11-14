package com.example.healthhelper.dietary.dataclasses.vo

import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Time

data class DiaryVO(
    @SerializedName("diaryID")
    var diaryID: Int = -1,

    @SerializedName("userID")
    var userID: Int = -1,

    @SerializedName("createDate")
    var createDate: Date = Date(0),

    @SerializedName("createTime")
    var createTime: Time = Time(0),

    @SerializedName("totalFat")
    var totalFat: Double = 0.0,

    @SerializedName("totalCarbon")
    var totalCarbon: Double = 0.0,

    @SerializedName("totalFiber")
    var totalFiber: Double = 0.0,

    @SerializedName("totalSugar")
    var totalSugar: Double = 0.0,

    @SerializedName("totalSodium")
    var totalSodium: Double = 0.0,

    @SerializedName("totalProtein")
    var totalProtein: Double = 0.0,

    @SerializedName("totalCalories")
    var totalCalories: Double = 0.0,
)