package com.example.healthhelper.dietary.dataclasses.vo

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MealsOptionVO(
    @DrawableRes val iconResId: Int,
    @StringRes val nameResId: Int,
    @StringRes val textResId: Int,
)