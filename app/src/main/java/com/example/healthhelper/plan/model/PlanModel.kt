package com.example.healthhelper.plan.model

import java.sql.Timestamp

data class PlanModel(
    var userDietPlanId: Int = 0,
    var startDateTime: Timestamp? = null,
    var endDateTime: Timestamp? = null,
    var categoryId: Int = 0,
    var categoryName: String = "",
) {
    override fun equals(other: Any?): Boolean {
        return this.userDietPlanId == (other as PlanModel).userDietPlanId
    }

    override fun hashCode(): Int {
        return userDietPlanId.hashCode()
    }
}