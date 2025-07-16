package com.example.healthhelper.planpage.data.model

import com.example.healthhelper.plan.model.PlanSpecificModel
import kotlinx.serialization.Serializable

@Serializable
data class AddPlanModel(
    val userId: Int,
    val startDateTime: String,
    val endDateTime: String,
    val categoryId: Int,
    val finishstate: Int,
    val fatgoal: Float,
    val carbongoal: Float,
    val proteingoal: Float,
    val Caloriesgoal: Float
){
    override fun equals(other: Any?): Boolean {
        return this.userId == (other as PlanSpecificModel).userDietPlanId
    }
    override fun hashCode(): Int {
        return userId.hashCode()
    }
}

@Serializable
data class GenericApiResponse(
    val result: Boolean,
    val errMsg: String? = null // 在 Kotlin 中，如果 JSON 中該字段為 null 或缺失，會正確映射為 null
)
