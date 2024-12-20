package com.example.healthhelper.person.personVM

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.person.model.DateRange
import com.example.healthhelper.person.model.ErrorMsg
import com.example.healthhelper.person.model.WeightData
import com.example.healthhelper.signuplogin.User
import com.example.healthhelper.signuplogin.UserManager
import com.example.healthhelper.web.httpPost
import com.example.healthhelper.web.serverUrl
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.pow

class WeightViewModel : ViewModel() {
    private val _weightDataState = MutableStateFlow<List<WeightData>>(emptyList())
    val weightDataState: StateFlow<List<WeightData>> = _weightDataState.asStateFlow()
    private val _dateRangeState = MutableStateFlow(DateRange())
    val dateRangeState: StateFlow<DateRange> = _dateRangeState.asStateFlow()
    val userId = UserManager.getUser().userId

    init {
        viewModelScope.launch {
            val defaultDateRange = _dateRangeState.value
            _weightDataState.value = fetchWeightDataList(
                defaultDateRange.startDate,
                defaultDateRange.endDate,
                userId
            )
        }
    }

    fun validateInput(height: String, weight: String, bodyFatValue: String, selectDate: String = ""): String? {
        val heightValue = height.toDoubleOrNull()
        val weightValue = weight.toDoubleOrNull()
        val bodyFatValue = bodyFatValue.toDoubleOrNull()

        if (heightValue == null || heightValue <= 0) {
            return "身高必須大於 0"
        }
        if (weightValue == null || weightValue <= 0) {
            return "體重必須大於 0"
        }
        if (bodyFatValue == null || bodyFatValue < 0) {
            return "體脂數值非負數"
        }
        if (weightDataState.value.any { it.recordDate == selectDate }) {
            return "該日期的體重數據已存在，請選擇其他日期"
        }
        return null
    }


    fun calculateBMI(height: Double, weight: Double): Double {
        return String.format("%.1f", weight / (height / 100).pow(2)).toDouble()
    }

    suspend fun updateBodyDataJson(
        recordId: Int,
        height: Double,
        weight: Double,
        bodyFat: Double,
        bmi: Double,
    ): Boolean {
        val url = "$serverUrl/updateBodyData"
        val gson = Gson()
        val jsonObject = JsonObject().apply {
            addProperty("recordId", recordId)
            addProperty("height", height)
            addProperty("weight", weight)
            addProperty("bodyFat", bodyFat)
            addProperty("bmi", bmi)
        }

        val result = httpPost(url, jsonObject.toString())
        val response = gson.fromJson(result, JsonObject::class.java)
        Log.d("dataout", response.get("errMsg").toString())

        if (response.get("result").asBoolean) {
            refreshWeightData()
        }
        return response.get("result").asBoolean
    }


    suspend fun deleteBodyDataJson(recordId: String): Boolean {
        val url = "$serverUrl/deleteBodyData"
        val gson = Gson()
        val jsonObject = JsonObject().apply {
            addProperty("recordId", recordId)
        }

        val result = httpPost(url, jsonObject.toString())
        val response = gson.fromJson(result, JsonObject::class.java)
        Log.d("dataout", response.get("errMsg").toString())

        if (response.get("result").asBoolean) {
            refreshWeightData()
        }
        return response.get("result").asBoolean
    }


    suspend fun insertBodyDataJson(
        height: Double,
        weight: Double,
        bodyFat: Double,
        recordDate: String,
        bmi: Double,
        userId: Int,
    ): Boolean {
        val url = "$serverUrl/insertBodyData"
        val gson = Gson()
        val jsonObject = JsonObject().apply {
            addProperty("userId", userId) // TODO: 需要修改 userId
            addProperty("height", height)
            addProperty("weight", weight)
            addProperty("bodyFat", bodyFat)
            addProperty("recordDate", recordDate)
            addProperty("bmi", bmi)
        }

        val result = httpPost(url, jsonObject.toString())
        val response = gson.fromJson(result, JsonObject::class.java)
        Log.d("dataout", response.get("errMsg").toString())
        if (response.get("result").asBoolean) {
            refreshWeightData()
        }
        ErrorMsg.errMsg = response.get("errMsg").toString()
        return response.get("result").asBoolean
    }


    fun setDateRange(startDate: String, endDate: String) {
        _dateRangeState.value = DateRange(startDate, endDate)
        viewModelScope.launch {
            _weightDataState.value = fetchWeightDataList(startDate, endDate, userId)
        }
    }


    fun refreshWeightData() {
        viewModelScope.launch {
            val dateRange = _dateRangeState.value
            _weightDataState.value =
                fetchWeightDataList(dateRange.startDate, dateRange.endDate, userId)
        }
    }

    fun filterWeightDataByRecordId(recordId: Int): List<WeightData> {
        return weightDataState.value.filter { it.recordId == recordId }
    }

    suspend fun fetchWeightDataList(
        startDate: String,
        endDate: String,
        userId: Int,
    ): List<WeightData> {
        val url = "$serverUrl/selectBodyData"
        val gson = Gson()
        val jsonObject = JsonObject().apply {
            addProperty("userId", userId)
            addProperty("startDate", startDate)
            addProperty("endDate", endDate)
        }

        return try {
            val result = httpPost(url, jsonObject.toString())
            val rootJsonObject = gson.fromJson(result, JsonObject::class.java)
            val dataJsonArray = rootJsonObject.getAsJsonArray("data")
            val collectionType = object : TypeToken<List<WeightData>>() {}.type
            gson.fromJson(dataJsonArray, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching WeightData from $url: ${e.message}", e)
            emptyList()
        }
    }
}
