package com.example.healthhelper.dietary.dataclasses.vo

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class SelectedDateVO(
    @SerializedName("userId")
    var userId:Int = -1,

    @SerializedName("createdate")
    var selectedDate:Date = Date(0),
)
