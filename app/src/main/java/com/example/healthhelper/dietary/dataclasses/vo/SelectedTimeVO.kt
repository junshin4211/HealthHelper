package com.example.healthhelper.dietary.dataclasses.vo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.gson.annotations.SerializedName
import java.sql.Time

data class SelectedTimeVO(
    @SerializedName("createtime")
    val selectedDate:MutableState<Time> = mutableStateOf(Time(0)),
)
