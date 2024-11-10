package com.example.healthhelper.plan.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.plan.PlanPage
import com.example.healthhelper.plan.PlanRepository
import com.example.healthhelper.plan.model.PlanModel
import com.example.healthhelper.signuplogin.UserManager
import com.example.healthhelper.web.httpPost
import com.example.healthhelper.web.serverUrl
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlanVM : ViewModel() {
    private val url = "$serverUrl/Plan"
    private val tag = "tag_PlanVM"

    private val repository = PlanRepository
    val myPlanState: StateFlow<PlanModel> = repository.myPlan
    val completePlanState: StateFlow<PlanModel> = repository.completePlan

    var panneelname: String = PlanPage.MyPlan.name
    var showdelete: Boolean = false
    val currentuserId = UserManager.getUser().userId

    init {
        getPlan()
        getCompletePlan()
    }

    private suspend fun fetchPlanData(
        userId: Int,
        finishstate: Int,
    ): PlanModel {
        return try {
            val gson = GsonBuilder()
                .setDateFormat("MMM dd, yyyy, HH:mm:ss") // 設置為 API 返回的日期格式
                .create()
            val jsonObject = JsonObject().apply {
                addProperty("userId", userId)
                addProperty("finishstate", finishstate)

            }

            val result = httpPost(url, jsonObject.toString())
            val collectionType = object : TypeToken<PlanModel>() {}.type
            gson.fromJson(result, collectionType) ?: PlanModel()
        } catch (e: Exception) {
            Log.e(tag, "Error in fetchPlanData: ${e.message}")
            return PlanModel()
        }
    }

    fun getPlan(){
        viewModelScope.launch {
            try {
                Log.d(tag,"$currentuserId")
                val myPlan = fetchPlanData(currentuserId, 0)
                repository.setMyPlan(myPlan)
                Log.d(tag, "Fetched myPlanState: ${myPlanState.value}")
            } catch (e: Exception) {
                Log.e(tag, "Error fetching myPlanState: ${e.message}")
            }
        }
    }

    fun getCompletePlan(){
        viewModelScope.launch {
            try {
                val completePlan = fetchPlanData(currentuserId, 1)
                repository.setCompletePlan(completePlan)
                Log.d(tag, "Fetched completePlanState: ${completePlanState.value}")
            } catch (e: Exception) {
                Log.e(tag, "Error fetching completePlanState: ${e.message}")
            }
        }
    }
}
