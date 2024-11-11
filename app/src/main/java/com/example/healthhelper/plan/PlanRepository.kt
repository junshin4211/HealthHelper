package com.example.healthhelper.plan

import com.example.healthhelper.plan.model.DiaryNutritionModel
import com.example.healthhelper.plan.model.PlanModel
import com.example.healthhelper.plan.model.PlanSpecificModel
import com.example.healthhelper.plan.model.PlanWithGoalModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet

object PlanRepository {
    //Setting Plan
    private val _setPlanState = MutableStateFlow(PlanWithGoalModel())
    val setPlanState: StateFlow<PlanWithGoalModel> = _setPlanState.asStateFlow()
    fun setPlan(plan: PlanWithGoalModel) {
        _setPlanState.value = plan
    }

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

    //remove myPlan from list
    fun removeMyPlan(plan: PlanModel) {
        _myPlanList.update {
            val plans = it.toMutableList()
            plans.remove(plan)
            plans
        }
    }

    //set plan from Plan selection
    private val _selectedPlan = MutableStateFlow(PlanModel())
    val selectedPlan: StateFlow<PlanModel> = _selectedPlan.asStateFlow()

    fun setSelectedPlan(plan: PlanModel) {
        _selectedPlan.updateAndGet {
            plan
        }
    }

    //get plan specific data
    private val _planSpecificData = MutableStateFlow(PlanSpecificModel())
    val planSpecificData: StateFlow<PlanSpecificModel> = _planSpecificData.asStateFlow()

    fun setPlanSpecificData(plan: PlanSpecificModel) {
        _planSpecificData.update {
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

    //remove CompletePlan from list
    fun removeCompletePlan(plan: PlanModel) {
        _completePlanList.update {
            val plans = it.toMutableList()
            plans.remove(plan)
            plans
        }
    }

    //get Diary Range List
    private val _diaryRangeList = MutableStateFlow(emptyList<DiaryNutritionModel>())
    val diaryRangeList: StateFlow<List<DiaryNutritionModel>> = _diaryRangeList.asStateFlow()

    fun setDiaryRangeList(list: List<DiaryNutritionModel>) {
        _diaryRangeList.update {
            list
        }
    }

}
