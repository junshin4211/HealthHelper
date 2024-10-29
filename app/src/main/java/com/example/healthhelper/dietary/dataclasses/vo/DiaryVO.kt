package com.example.healthhelper.dietary.dataclasses.vo

import com.google.gson.annotations.SerializedName

data class DiaryVO(
    @SerializedName("name")
    val name:String = "",

    @SerializedName("foodName")
    val foodName: String = "",

    @SerializedName("date")
    val date:String = "",

    @SerializedName("time")
    val time:String = "",
)