package com.example.healthhelper.dietary.dataclasses.vo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.google.gson.annotations.SerializedName
import java.sql.Date

data class SelectedDateVO(
    @SerializedName("userId")
    val userId:MutableState<Int> = mutableIntStateOf(-1),

    @SerializedName("createdate")
    val selectedDate:MutableState<Date> = mutableStateOf(Date(0)),
)
