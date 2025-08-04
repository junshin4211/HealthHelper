package com.example.healthhelper.planpage.data.model

import com.example.healthhelper.plan.model.DiaryNutritionModel
import kotlinx.serialization.Serializable

@Serializable
data class DiaryNutritionModel(
    val diaryId: Int,
    val userId: Int,
    val createDate: String,
    val totalFat: Float,
    val totalCarbon: Float,
    val totalFiber: Float,
    val totalSugar: Float,
    val totalSodium: Float,
    val totalProtein: Float,
    val totalCalories: Float
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true // 同一個實例
        if (other !is DiaryNutritionModel) return false // 類型不同，或者 other 是 null

        return diaryId == other.diaryId
    }

    override fun hashCode(): Int {
        return diaryId.hashCode()
    }

}
