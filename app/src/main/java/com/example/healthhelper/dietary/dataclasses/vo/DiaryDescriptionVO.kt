package com.example.healthhelper.dietary.dataclasses.vo

import com.google.gson.annotations.SerializedName

data class DiaryDescriptionVO(
    @SerializedName("diaryID")
    var diaryID: Int,

    @SerializedName("mealCategoryID")
    var mealCategoryID: Int,

    @SerializedName("foodIconUri")
    var uri: String? = null,

    @SerializedName("foodDescription")
    var description: String = "",
)
