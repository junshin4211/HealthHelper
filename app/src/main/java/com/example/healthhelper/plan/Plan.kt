package com.example.healthhelper.plan

import java.security.Timestamp
import java.util.Date

data class Plan(
    var planId: Int,
    var categoryId: Int,
    var categoryName: String,
    var startTime: String?,//var startTime: Timestamp?,
    var endTime: String?,//var endTime: Timestamp?,
    var fatGoal: Float,
    var carbonGoal: Float,
    var proteinGoal: Float,
    var calorieGoal: Float,
) {
    override fun equals(other: Any?): Boolean {
        return this.planId == (other as Plan).planId
    }

    override fun hashCode(): Int {
        return planId.hashCode()
    }
}