package com.example.healthhelper.dietary.dataclasses.vo

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.gson.annotations.SerializedName

data class DiaryDescriptionVO(
    @SerializedName("diaryID")
    var diaryID: Int,

    @SerializedName("mealCategoryID")
    var mealCategoryID: Int,

    @SerializedName("foodIconUri")
    var uri: MutableState<Uri?> = mutableStateOf(null),

    @SerializedName("foodDescription")
    var description: MutableState<String> = mutableStateOf(""),
)
