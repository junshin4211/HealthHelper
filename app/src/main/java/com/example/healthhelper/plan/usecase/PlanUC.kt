package com.example.healthhelper.plan.usecase

import com.example.healthhelper.plan.PlanPage
import com.example.healthhelper.plan.viewmodel.ManagePlanVM
import com.example.healthhelper.plan.viewmodel.PlanVM
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZonedDateTime

interface PlanUC {
    fun dateTimeFormat(datetime:Any?):String
    fun stringToTimeStamp(datetime:LocalDateTime): Timestamp?
    fun InitialDefaultGoal(planName: PlanPage,
                           onSetGoal: (fat: Float, carb: Float, protein: Float) -> Unit,
                           onSetCateId: (cateId: Int) -> Unit)
    fun customPlanInitial(planName: PlanPage,
                          onSetCateId: (cateId: Int) -> Unit)
    fun fetchSingle(planVM: PlanVM)
    fun fetchList(managePlanVM: ManagePlanVM)
    fun percentToGram(nutrition: String, calorie:Float,percent: Float ,onSetGram: (grams:Float)->Unit)
}