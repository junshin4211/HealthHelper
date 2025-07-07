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
        return this.userDietPlanId == (other as PlanSpecificModel).userDietPlanId
    }
    override fun hashCode(): Int {
        return userDietPlanId.hashCode()
    }
}
