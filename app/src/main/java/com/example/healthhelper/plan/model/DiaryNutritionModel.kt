package com.example.healthhelper.plan.model

import java.util.Date

data class DiaryNutritionModel(
    val diaryID: Int = -1,
    val userID: Int = -1,
    val createdate: Date = Date(0),
    val totalFat: Float = 0.0f,
    val totalCarbon: Float = 0.0f,
    val totalFiber: Float = 0.0f,
    val totalSugar: Float = 0.0f,
    val totalSodium: Float = 0.0f,
    val totalProtein: Float = 0.0f,
    val totalCalories: Float = 0.0f
) {
    override fun equals(other: Any?): Boolean {
        return this.diaryID == (other as DiaryNutritionModel).diaryID
    }

    override fun hashCode(): Int {
        return diaryID.hashCode()
    }

}