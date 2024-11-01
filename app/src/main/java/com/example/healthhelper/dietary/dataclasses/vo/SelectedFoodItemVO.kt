package com.example.healthhelper.dietary.dataclasses.vo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class SelectedFoodItemVO(
    val name:String,
    val grams:Double,
    var isCheckedWhenQuery: MutableState<Boolean> = mutableStateOf(false),
    var isCheckedWhenSelection: MutableState<Boolean> = mutableStateOf(false),
    var isCheckingWhenQuery: MutableState<Boolean> = mutableStateOf(false),
    var isCheckingWhenSelection: MutableState<Boolean> = mutableStateOf(false),
)