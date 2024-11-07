package com.example.healthhelper.dietary.dataclasses.vo.other

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class EnterStatusVO(
    val isFirstEnter: MutableState<Boolean> = mutableStateOf(true),
)