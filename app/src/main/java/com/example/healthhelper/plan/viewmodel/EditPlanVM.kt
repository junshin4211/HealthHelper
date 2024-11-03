package com.example.healthhelper.plan.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.healthhelper.plan.PlanRepository
import com.example.healthhelper.plan.model.PlanModel
import com.example.healthhelper.plan.model.PlanWithGoalModel
import com.example.healthhelper.web.httpPost
import com.example.healthhelper.web.serverUrl
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.sql.Timestamp

class EditPlanVM:ViewModel() {
    private val tag = "tag_EditVM"
    private val repository = PlanRepository

    private suspend fun insertPlanRequest(
        planGoal: PlanWithGoalModel,
//        categoryId: Int,
//        userId: Int,
//        startDateTime: Timestamp,
//        endDateTime: Timestamp,
//        finishState: Int,
//        fatgoal: Float,
//        carbongoal: Float,
//        proteingoal: Float,
//        Caloriesgoal: Float
    ):Boolean {
        try {
            val url = "$serverUrl/Plan/DeletePlan"
            val gson = Gson()
            var jsonObject = JsonObject()

            jsonObject.addProperty("categoryId", planGoal.categoryId)
            jsonObject.addProperty("userId", planGoal.userId)
            jsonObject.addProperty("startDateTime", planGoal.startDateTime?.toInstant().toString())
            jsonObject.addProperty("endDateTime", planGoal.endDateTime?.toInstant().toString())
            jsonObject.addProperty("finishState", planGoal.finishState)
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