package com.example.healthhelper.enumclass

import androidx.annotation.StringRes
import com.example.healthhelper.R

enum class ScreenEnum(
    @StringRes val title:Int
){
    HomePage(title = R.string.home_page),
    DietDiaryMainFrame(title = R.string.diet_diary_main_frame),
}