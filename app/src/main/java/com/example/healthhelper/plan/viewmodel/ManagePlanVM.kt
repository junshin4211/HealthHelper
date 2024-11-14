package com.example.healthhelper.plan.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.plan.PlanRepository
import com.example.healthhelper.plan.model.PlanModel
import com.example.healthhelper.signuplogin.UserManager
import com.example.healthhelper.web.httpPost
import com.example.healthhelper.web.serverUrl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ManagePlanVM : ViewModel() {
    private val tag = "tag_ManageVM"

    private val repository = PlanRepository
    val myPlanListState: StateFlow<List<PlanModel>> = repository.myPlanList
    val completePlanListState: StateFlow<List<PlanModel>> = repository.completePlanList
    val currentuserId = UserManager.getUser().userId

    init {
        getPlanList()
        getCompletePlanList()
    }


    private suspend fun fetchPlanData(
        userId: Int,
        finishState: Int,
    ): List<PlanModel> {
        val url = "$serverUrl/Plan/Manage"
        return try {
            val gson = GsonBuilder()
                .setDateFormat("MMM dd, yyyy, HH:mm:ss") // 設置為 API 返回的日期格式
                .create()
            val jsonObject = JsonObject().apply {
                addProperty("userId", userId)
                addProperty("finishstate", finishState)
            }

            val result = httpPost(url, jsonObject.toString())
            val collectionType = object : TypeToken<List<PlanModel>>() {}.type
            gson.fromJson(result, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e(tag, "Error in fetchPlanData: ${e.message}")
            emptyList()
        }
    }

    private suspend fun deletePlanRequest(
        userId: Int,
        userDietPlanID: Int,
        finishState: Int,
    ): Boolean {
        try {
            val url = "$serverUrl/Plan/DeletePlan"
            val gson = Gson()
            var jsonObject = JsonObject()

            jsonObject.addProperty("userId", userId)
            jsonObject.addProperty("userDietPlanId", userDietPlanID)
            jsonObject.addProperty("finishstate", finishState)


            val result = httpPost(url, jsonObject.toString())
            jsonObject = gson.fromJson(result, JsonObject::class.java)
            Log.d(tag, "deletePlan: $result")
            return jsonObject.get("result").asBoolean
        } catch (e: Exception) {
            Log.e(tag, "Error to delete plan: ${e.message}")
            return false
        }
    }

    suspend fun deletePlan(
        plan: PlanModel,
        userDietPlanID: Int,
        finishState: Int,
    ):Boolean {
        if (deletePlanRequest(currentuserId, userDietPlanID, finishState)) {
            when (finishState) {
                0 ->{
                    repository.removeMyPlan(plan)
                    return true
                }
                1 ->{
                    repository.removeCompletePlan(plan)
                    return true
                }
            }

        }
        return false
    }

    fun getPlanList() {
        viewModelScope.launch {
            try {
                val myPlanList = fetchPlanData(currentuserId, 0)
                repository.setMyPlanList(myPlanList)
                Log.d(tag, "Fetched myPlanState: ${myPlanListState.value}")
            } catch (e: Exception) {
                Log.e(tag, "Error fetching myPlanState: ${e.message}")
            }
        }
    }

    fun getCompletePlanList() {
        viewModelScope.launch {
            try {
                // 使用 async 並行執行兩個 suspend 函數
                val completePlanListDeferred = async { fetchPlanData(currentuserId, 1) }
                val completePlanArchiveDeferred = async { fetchPlanData(currentuserId, 2) }

                // 等待兩個 async 都完成後再繼續
                val completePlanList = completePlanListDeferred.await()
                val completePlanArchive = completePlanArchiveDeferred.await()

                // 合併並排序列表
                val combinedList = (completePlanList + completePlanArchive).sortedBy { it.startDateTime }

                // 存儲排序後的列表
                repository.setCompletePlanList(combinedList)

                Log.d(tag, "Fetched completePlanState: ${combinedList.size} items")
            } catch (e: Exception) {
                Log.e(tag, "Error fetching completePlanState: ${e.message}")
            }
        }

    }
}