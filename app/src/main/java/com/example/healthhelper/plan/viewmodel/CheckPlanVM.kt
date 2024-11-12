package com.example.healthhelper.plan.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.plan.PlanRepository
import com.example.healthhelper.plan.model.DiaryNutritionModel
import com.example.healthhelper.plan.model.PlanModel
import com.example.healthhelper.plan.model.PlanSpecificModel
import com.example.healthhelper.plan.usecase.PlanUCImpl
import com.example.healthhelper.signuplogin.UserManager
import com.example.healthhelper.web.httpPost
import com.example.healthhelper.web.serverUrl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.time.*
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CheckPlanVM: ViewModel() {
    private val tag = "tag_CheckPlanVM"
    private val repository = PlanRepository
    private val currentUser = UserManager.getUser()
    val planUCImpl = PlanUCImpl()

    val selectedPlanState: StateFlow<PlanModel> = repository.selectedPlan
    val planSpecificState: StateFlow<PlanSpecificModel> = repository.planSpecificData
    val diaryRangeListState: StateFlow<List<DiaryNutritionModel>> = repository.diaryRangeList
    val currentuserId = UserManager.getUser().userId

    fun getCateGoryName(): String{
        return selectedPlanState.value.categoryName
    }

    //set selected plan
    fun setSelectedPlan(plan: PlanModel) {
        repository.setSelectedPlan(plan)
    }

    fun clear(){
        setSelectedPlan(PlanModel())
        repository.setPlanSpecificData(PlanSpecificModel())
        repository.setDiaryRangeList(emptyList())
    }

    //get specific plan request
    private suspend fun fetchSpecificPlan(
        userId: Int,
        plan: PlanModel,
    ): PlanSpecificModel{
        val url = "$serverUrl/Plan/SelectPlan"
        return try {
            val gson = GsonBuilder()
                .registerTypeAdapter(Date::class.java, DateDeserializer())
                .create()
            val jsonObject = JsonObject().apply {
                addProperty("userId", userId)
                addProperty("userDietPlanId", plan.userDietPlanId)
            }

            val result = httpPost(url, jsonObject.toString())
            val collectionType = object : TypeToken<PlanSpecificModel>() {}.type
            gson.fromJson(result, collectionType) ?: PlanSpecificModel()
        } catch (e: Exception) {
            Log.e(tag, "Error in fetchSpecificPlan: ${e.message}")
            return PlanSpecificModel()
        }
    }

    //get diary List request
    private suspend fun fetchDiaryList(
        planSpecificModel: PlanSpecificModel,
    ):List<DiaryNutritionModel>{
        val url = "$serverUrl/Plan/DiaryList"
        return try {
            val gson = GsonBuilder()
                .registerTypeAdapter(Date::class.java, DateDeserializerDiary())
                .create()
            Log.d(tag, "datein: ${planSpecificModel.startDateTime}")
            val startDate = planUCImpl.dateTimeFormat(planSpecificModel.startDateTime)
            Log.d(tag, "startDate: $startDate")
            Log.d(tag, "datein: ${planSpecificModel.endDateTime}")
            val endDate = planUCImpl.dateTimeFormat(planSpecificModel.endDateTime)
            Log.d(tag, "endDate: $endDate")
            val jsonObject = JsonObject().apply {
                addProperty("userId", planSpecificModel.userId)
                addProperty("startDate", startDate)
                addProperty("endDate", endDate)
            }

            val result = httpPost(url, jsonObject.toString())
            val collectionType = object : TypeToken<List<DiaryNutritionModel>>() {}.type
            gson.fromJson(result, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e(tag, "Error in fetchDiaryList: ${e.message}")
            return emptyList()
        }
    }

    //update finished plan request
    private suspend fun updatefinishrequest(
        userId: Int,
        userDietPlanID: Int,
        finishstate: Int
    ): Boolean{
        try {
            val url = "$serverUrl/Plan/UpdatePlan"
            val gson = Gson()
            var jsonObject = JsonObject()

            jsonObject.addProperty("userId", userId)
            jsonObject.addProperty("userDietPlanId", userDietPlanID)
            jsonObject.addProperty("finishstate", finishstate)

            val result = httpPost(url, jsonObject.toString())
            jsonObject = gson.fromJson(result, JsonObject::class.java)
            Log.d(tag, "updatePlan: $result")
            return jsonObject.get("result").asBoolean
        }catch (e:Exception){
            Log.e(tag, "Error to update plan: ${e.message}")
            return  false
        }
    }

    //get specific plan
    fun getSpecificPlan(){
        viewModelScope.launch {
            try {
                val specificPlan = fetchSpecificPlan(currentuserId, selectedPlanState.value)
                Log.d(tag, "getspecificPlan: $specificPlan")
                repository.setPlanSpecificData(specificPlan)
                Log.d(tag, "Fetched planSpecificState: ${planSpecificState.value}")
            } catch (e: Exception) {
                Log.e(tag, "Error fetching planSpecificState: ${e.message}")
            }
        }
    }

    //get diary list
    fun getDiaryList(){
        viewModelScope.launch {
            try {
                Log.d(tag, "input quest data: ${planSpecificState.value}")
                val diaryList = fetchDiaryList(planSpecificState.value)
                Log.d(tag, "getDiaryList: $diaryList")
                repository.setDiaryRangeList(diaryList)
                Log.d(tag, "Fetched diaryRangeListState: ${diaryRangeListState.value}")
            } catch (e: Exception) {
                Log.e(tag, "Error fetching diaryRangeListState: ${e.message}")
            }
        }
    }

    //update plan
    suspend fun updatePlan(userDietPlanID: Int,finishstate: Int): Boolean{
        try {
            val result = updatefinishrequest(currentuserId, userDietPlanID, finishstate)
            return result
        }catch (e: Exception) {
            Log.e(tag, "Error update plan state: ${e.message}")
            return false
        }
    }

    //反序列Specific
    class DateDeserializer : JsonDeserializer<Date> {
        private val dateFormats = listOf(
            "MMM d, yyyy, h:mm:ss a", // API 返回的格式
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSS", // ISO 8601 格式
            "yyyy-MM-dd" // 常見日期格式
        )

        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date {
            // 移除窄不換行空格（NARROW NO-BREAK SPACE, Unicode: U+202F）並替換為標準空格
            val dateString = json.asString.replace("\u202F", " ")

            for (format in dateFormats) {
                try {
                    val sdf = SimpleDateFormat(format, Locale.US)
                    sdf.timeZone = TimeZone.getTimeZone("Asia/Taipei")
                    return sdf.parse(dateString)
                } catch (e: Exception) {
                    // 繼續嘗試下一個格式
                }
            }
            throw JsonParseException("Unparseable date: \"$dateString\"")
        }
    }

    //反序列Diary
    class DateDeserializerDiary : JsonDeserializer<Date> {
        private val formats = listOf(
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()), // ISO格式
            SimpleDateFormat("MMM d, yyyy", Locale.US), // 英文格式
            SimpleDateFormat("MMM d, yyyy", Locale.CHINESE) // 中文格式
        )

        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date? {
            json?.asString?.let { dateString ->
                for (format in formats) {
                    try {
                        return format.parse(dateString)
                    } catch (e: Exception) {
                        // 忽略當前格式的錯誤，嘗試下一個格式
                    }
                }
            }
            throw JsonParseException("Unparseable date: \"$json\". Supported formats: ${formats.map { it.toPattern() }}")
        }
    }
}