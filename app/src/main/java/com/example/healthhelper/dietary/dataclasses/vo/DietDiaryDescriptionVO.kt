package com.example.healthhelper.dietary.dataclasses.vo

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class DietDiaryDescriptionVO(
    var uri: Uri? = null,
    val description: MutableState<String> = mutableStateOf(""),
)
