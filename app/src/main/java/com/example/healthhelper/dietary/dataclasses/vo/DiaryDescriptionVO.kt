package com.example.healthhelper.dietary.dataclasses.vo

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class DiaryDescriptionVO(
    @SerializedName("diaryId")
    var diaryId: Int,

    @SerializedName("foodIconUri")
    var uri: Uri? = null,

    @SerializedName("foodDescription")
    var description: String = "",
)
