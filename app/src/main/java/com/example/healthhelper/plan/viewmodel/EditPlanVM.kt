package com.example.healthhelper.plan.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.plan.PlanRepository
import com.example.healthhelper.plan.model.PlanWithGoalModel
import com.example.healthhelper.signuplogin.UserManager
import com.example.healthhelper.web.httpPost
import com.example.healthhelper.web.serverUrl
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Timestamp

class EditPlanVM:ViewModel() {
    private val tag = "tag_EditVM"
    private val repository = PlanRepository
    //get the insert plan value from here
    val planSetState: StateFlow<PlanWithGoalModel> = repository.setPlanState
//    val currentuserId = UserManager.getUser().userId

    //update plan value by function
    fun updateCategoryId(categoryId: Int) {
        val currentPlan = planSetState.value
        repository.setPlan(currentPlan.apply { this.categoryId = categoryId })
    }

    fun updateUserId(userId: Int) {
        val currentPlan = planSetState.value
        repository.setPlan(currentPlan.apply { this.userId = userId })
    }

    fun updateStartDateTime(startDateTime: Timestamp?) {
        val currentPlan = planSetState.value
        repository.setPlan(currentPlan.apply { this.startDateTime = startDateTime })
    }

    fun updateEndDateTime(endDateTime: Timestamp?) {
        val currentPlan = planSetState.value
        repository.setPlan(currentPlan.apply { this.endDateTime = endDateTime })
    }

    fun updateFinishState(finishState: Int) {
        val currentPlan = planSetState.value
        repository.setPlan(currentPlan.apply { this.finishState = finishState })
    }

    fun updateFatGoal(fatGoal: Float) {
        val currentPlan = planSetState.value
        repository.setPlan(currentPlan.apply { this.fatgoal = fatGoal })
    }

    fun updateCarbGoal(carbGoal: Float) {
        val currentPlan = planSetState.value
        repository.setPlan(currentPlan.apply { this.carbongoal = carbGoal })
    }

    fun updateProteinGoal(proteinGoal: Float) {
        val currentPlan = planSetState.value
        repository.setPlan(currentPlan.apply { this.proteingoal = proteinGoal })
    }

    fun updateCaloriesGoal(caloriesGoal: Float) {
        val currentPlan = planSetState.value
        repository.setPlan(currentPlan.apply { this.Caloriesgoal = caloriesGoal })
    }

    //insert plan call here
    suspend fun insertPlan(): Boolean {
        val currentPlan = planSetState.value
        return withContext(Dispatchers.IO) {
            val result = insertPlanRequest(currentPlan)
            if (result) {
                Log.d(tag, "Plan inserted successfully")
            } else {
                Log.e(tag, "Failed to insert plan")
            }
            result
        }
    }

    //insert plan request
    private suspend fun insertPlanRequest(
        planGoal: PlanWithGoalModel,
    ):Boolean {
        try {
            val url = "$serverUrl/Plan/AddPlan"
            val gson = Gson()
            var jsonObject = JsonObject()

            jsonObject.addProperty("categoryId", planGoal.categoryId)
            jsonObject.addProperty("userId", planGoal.userId)
            jsonObject.addProperty("startDateTime", planGoal.startDateTime?.toInstant().toString())
            jsonObject.addProperty("endDateTime", planGoal.endDateTime?.toInstant().toString())
            jsonObject.addProperty("finishstate", planGoal.finishState)
            jsonObject.addProperty("fatgoal", planGoal.fatgoal)
            jsonObject.addProperty("carbongoal", planGoal.carbongoal)
            jsonObject.addProperty("proteingoal", planGoal.proteingoal)
            jsonObject.addProperty("Caloriesgoal", planGoal.Caloriesgoal)
            val result = httpPost(url, jsonObject.toString())
            jsonObject = gson.fromJson(result, JsonObject::class.java)
            Log.d(tag, "insertPlan: $result")
            return jsonObject.get("result").asBoolean
        }catch (e:Exception)
        {
            Log.e(tag, "Error to insert plan: ${e.message}")
            return false
        }
    }

}