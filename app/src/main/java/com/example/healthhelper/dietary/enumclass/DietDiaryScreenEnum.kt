package com.example.healthhelper.dietary.enumclass

import androidx.annotation.StringRes
import com.example.healthhelper.R

enum class DietDiaryScreenEnum(
    @StringRes val title: Int,
){
    DietDiaryMainFrame(title = R.string.diet_diary_main_frame),
    DietDiaryMealFrame(title = R.string.diet_diary_meal_frame),
    AddNewDietDiaryItemFrame(title = R.string.add_new_diet_diary_item_frame),
    FoodItemInfoFrame(title = R.string.food_item_info_frame),
    SearchHintFrame(title = R.string.search_hint_frame),
}