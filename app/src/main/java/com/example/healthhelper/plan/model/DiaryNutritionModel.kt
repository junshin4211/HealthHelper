package com.example.healthhelper.plan.model

import java.util.Date

data class DiaryNutritionModel(
    val diaryId: Int = -1,
    val userId: Int = -1,
    val createDate: Date = Date(0),
    val totalFat: Float = 0.0f,
    val totalCarbon: Float = 0.0f,
    val totalFiber: Float = 0.0f,
    val totalSugar: Float = 0.0f,
    val totalSodium: Float = 0.0f,
    val totalProtein: Float = 0.0f,
    val totalCalories: Float = 0.0f
) {
    override fun equals(other: Any?): Boolean {
        return this.diaryId == (other as DiaryNutritionModel).diaryId
    }

    override fun hashCode(): Int {
        return diaryId.hashCode()
    }

}