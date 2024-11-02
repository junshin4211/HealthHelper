package com.example.healthhelper.plan

import com.example.healthhelper.plan.model.PlanModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object PlanRepository {
    //Get Single Plan
    private val _myPlan = MutableStateFlow(PlanModel())
    val myPlan: StateFlow<PlanModel> = _myPlan.asStateFlow()
    fun setMyPlan(plan: PlanModel) {
        _myPlan.update {
            plan
        }
    }

    private val _completePlan = MutableStateFlow(PlanModel())
    val completePlan: StateFlow<PlanModel> = _completePlan.asStateFlow()
    fun setCompletePlan(plan: PlanModel) {
        _completePlan.update {
            plan
        }
    }

    //Get List Plan
    private val _myPlanList = MutableStateFlow(emptyList<PlanModel>())
    val myPlanList: StateFlow<List<PlanModel>> = _myPlanList.asStateFlow()

    fun setMyPlanList(list: List<PlanModel>) {
        _myPlanList.update {
            list
        }
    }

    private val _completePlanList = MutableStateFlow(emptyList<PlanModel>())
    val completePlanList: StateFlow<List<PlanModel>> = _completePlanList.asStateFlow()

    fun setCompletePlanList(list: List<PlanModel>) {
       _completePlanList.update {
           list
       }
    }
}
