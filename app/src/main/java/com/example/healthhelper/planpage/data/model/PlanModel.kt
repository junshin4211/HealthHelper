package com.example.healthhelper.planpage.data.model

import com.example.healthhelper.plan.model.PlanSpecificModel
import kotlinx.serialization.Serializable

@Serializable
data class PlanModel(
    val userDietPlanId: Int,
    val startDateTime: String,
    val endDateTime: String,
    val categoryId: Int,
    val categoryName: String,
    val finishstate: Int,
    val fatgoal: Float,
    val carbongoal: Float,
    val proteingoal: Float,
    val Caloriesgoal: Float
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true // 同一個實例
        if (other !is PlanModel) return false // 類型不同，或者 other 是 null

        return userDietPlanId == other.userDietPlanId
    }
    override fun hashCode(): Int {
        return userDietPlanId.hashCode()
    }
}
