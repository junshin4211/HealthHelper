package com.example.healthhelper.dietary.enumclass

import androidx.annotation.StringRes
import com.example.healthhelper.R

enum class MealCategoryEnum(@StringRes val title:Int){
    BREAKFAST(title = R.string.breakfast),
    LUNCH(title = R.string.lunch),
    DINNER(title = R.string.dinner),
    SUPPER(title = R.string.supper),
    EMPTY_STRING(title = R.string.empty_string)
}