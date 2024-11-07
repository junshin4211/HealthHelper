package com.example.healthhelper.plan.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.plan.PlanRepository
import com.example.healthhelper.plan.model.DiaryNutritionModel
import com.example.healthhelper.plan.model.PlanModel
import com.example.healthhelper.plan.model.PlanSpecificModel
import com.example.healthhelper.plan.model.PlanWithGoalModel
import com.example.healthhelper.signuplogin.UserManager
import com.example.healthhelper.web.httpPost
import com.example.healthhelper.web.serverUrl
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckPlanVM: ViewModel() {
    private val tag = "tag_CheckPlanVM"
    private val repository = PlanRepository
    private val currentUser = UserManager.getUser()

    val selectedPlanState: StateFlow<PlanModel> = repository.selectedPlan
    val planSpecificState: StateFlow<PlanSpecificModel> = repository.planSpecificData
    val diaryRangeListState: StateFlow<List<DiaryNutritionModel>> = repository.diaryRangeList

    init {
        getSpecificPlan()
    }

    fun getCateGoryName(): String{
        return selectedPlanState.value.categoryName
    }

    //set selected plan
    fun setSelectedPlan(plan: PlanModel) {
        repository.setSelectedPlan(plan)
    }

    //get specific plan request
    private suspend fun fetchSpecificPlan(
        userId: Int,
        plan: PlanModel
    ): PlanSpecificModel{
        val url = "$serverUrl/Plan/SpecificPlan"
        return try {
            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd") // 設置為 API 返回的日期格式
                .create()
            val jsonObject = JsonObject().apply {
                addProperty("userId", userId)
                addProperty("userDietPlanId", plan.userDietPlanId)
            }

            val result = httpPost(url, jsonObject.toString())
            val collectionType = object : TypeToken<PlanSpecificModel>() {}.type
            gson.fromJson(result, collectionType) ?: PlanSpecificModel()
        } catch (e: Exception) {
            Log.e(tag, "Error in fetchPlanData: ${e.message}")
            return PlanSpecificModel()
        }
    }

    //get specific plan
    fun getSpecificPlan(){
        viewModelScope.launch {
            try {
                val specificPlan = fetchSpecificPlan(2, selectedPlanState.value)
                repository.setPlanSpecificData(specificPlan)
                Log.d(tag, "Fetched planSpecificState: ${planSpecificState.value}")
            } catch (e: Exception) {
                Log.e(tag, "Error fetching planSpecificState: ${e.message}")
            }
        }
    }
}