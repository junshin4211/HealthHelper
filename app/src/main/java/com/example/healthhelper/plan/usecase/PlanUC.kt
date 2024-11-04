package com.example.healthhelper.plan.usecase

import com.example.healthhelper.plan.PlanPage
import java.sql.Timestamp
import java.time.LocalDateTime

interface PlanUC {
    fun dateTimeFormat(datetime:Any?):String
    fun stringToTimeStamp(datetime:LocalDateTime): Timestamp?
    fun planInitial(planName: PlanPage,
                    onSetGoal: (fat: Float, carb: Float, protein: Float) -> Unit,
                    onSetCateId: (cateId: Int) -> Unit)
}