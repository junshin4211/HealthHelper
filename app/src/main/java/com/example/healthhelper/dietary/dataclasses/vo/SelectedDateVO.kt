package com.example.healthhelper.dietary.dataclasses.vo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.gson.annotations.SerializedName
import java.sql.Date

data class SelectedDateVO(
    @SerializedName("createdate")
    val selectedDate:MutableState<Date> = mutableStateOf(Date(0)),
)
