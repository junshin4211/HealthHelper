package com.example.healthhelper.dietary.dataclasses.vo

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class DiaryDescriptionVO(
    @SerializedName("diaryID")
    var diaryID: Int,

    @SerializedName("foodIconUri")
    var uri: Uri? = null,

    @SerializedName("foodDescription")
    var description: String = "",
)
