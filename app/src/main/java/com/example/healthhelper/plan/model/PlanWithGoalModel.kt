package com.example.healthhelper.plan.model

import java.sql.Timestamp

data class PlanWithGoalModel (
    var categoryId: Int = 0,
    var userId: Int = 0,
    var startDateTime: Timestamp? = null,
    var endDateTime: Timestamp? = null,
    var finishState: Int = 0,
    var fatgoal: Float = 0.0f,
    var carbongoal: Float = 0.0f,
    var proteingoal: Float = 0.0f,
    var Caloriesgoal: Float = 0.0f,
) {
    override fun equals(other: Any?): Boolean {
        return this.userId == (other as PlanWithGoalModel).userId
    }
    override fun hashCode(): Int {
        return userId.hashCode()
    }


}