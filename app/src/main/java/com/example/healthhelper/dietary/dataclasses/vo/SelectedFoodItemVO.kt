package com.example.healthhelper.dietary.dataclasses.vo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.healthhelper.dietary.enumclass.MealCategoryEnum

data class SelectedFoodItemVO(
    val name:String,
    var meal:MutableState<String> = mutableStateOf(MealCategoryEnum.BREAKFAST.name),
    var grams:MutableState<Double> = mutableDoubleStateOf(100.0),
    var isCheckedWhenQuery: MutableState<Boolean> = mutableStateOf(false),
    var isCheckedWhenSelection: MutableState<Boolean> = mutableStateOf(false),
    var isCheckingWhenQuery: MutableState<Boolean> = mutableStateOf(false),
    var isCheckingWhenSelection: MutableState<Boolean> = mutableStateOf(false),
)