package com.example.healthhelper.dietary.dataclasses.vo

import androidx.annotation.DrawableRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class DietDiaryDescriptionVO(
    @DrawableRes var iconResId: Int,
    val description: MutableState<String> = mutableStateOf(""),
)
