package com.example.healthhelper.dietary.dataclasses.vo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class SelectedFoodItemVO(
    val name:String,
    val grams:Double,
    var isSelected: MutableState<Boolean> = mutableStateOf(false),
)