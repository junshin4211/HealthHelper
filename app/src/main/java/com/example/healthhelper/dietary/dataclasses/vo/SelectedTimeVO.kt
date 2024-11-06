package com.example.healthhelper.dietary.dataclasses.vo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.google.gson.annotations.SerializedName
import java.sql.Time

data class SelectedTimeVO(
    @SerializedName("userId")
    val userId:MutableState<Int> = mutableIntStateOf(-1),
    
    @SerializedName("createtime")
    val selectedDate:MutableState<Time> = mutableStateOf(Time(0)),
)
