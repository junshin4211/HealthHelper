package com.example.healthhelper.planpage.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DeletePlanModel(
    val userId: Int,
    val userDietPlanId: Int,
    val finishstate: Int
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true // 同一個實例
        if (other !is DeletePlanModel) return false // 類型不同，或者 other 是 null

        return userId == other.userId
    }
    override fun hashCode(): Int {
        return userId.hashCode()
    }
}
