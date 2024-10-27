package com.example.healthhelper.person.model

import android.graphics.Bitmap

data class AchievementData(
    val id: String,
    val atype: String,
//    val aIcon: Bitmap,
    val aIcon: Int,
    val aname: String,
    val acontent: String,
    val finishDate: String
)