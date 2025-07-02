package com.example.healthhelper.planpage.data.model

import com.example.healthhelper.plan.model.DiaryNutritionModel

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
        return this.diaryId == (other as DiaryNutritionModel).diaryId
    }

    override fun hashCode(): Int {
        return diaryId.hashCode()
    }

}
