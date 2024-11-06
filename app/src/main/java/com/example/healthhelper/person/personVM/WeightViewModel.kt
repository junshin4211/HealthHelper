package com.example.healthhelper.person.personVM

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.person.model.DateRange
import com.example.healthhelper.person.model.ErrorMsg
import com.example.healthhelper.person.model.WeightData
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


    init {
        viewModelScope.launch {
            val defaultDateRange = _dateRangeState.value
            _weightDataState.value = fetchWeightDataList(
                defaultDateRange.startDate,
                defaultDateRange.endDate
            )
        }
    }


    fun calculateBMI(height: Double, weight: Double): Double {
        return String.format("%.1f", weight / (height / 100).pow(2)).toDouble()
    }

    suspend fun updateBodyDataJson(recordId: Int, height: Double, weight: Double, bodyFat: Double, recordDate: String, bmi: Double): Boolean {
        val url = "$serverUrl/updateBodyData"
        val gson = Gson()
        val jsonObject = JsonObject().apply {
            addProperty("recordId", recordId)
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


    suspend fun insertBodyDataJson(height: Double, weight: Double, bodyFat: Double, recordDate: String, bmi: Double): Boolean {
        val url = "$serverUrl/insertBodyData"
        val gson = Gson()
        val jsonObject = JsonObject().apply {
            addProperty("userId", 2) // TODO: 需要修改 userId
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
            _weightDataState.value = fetchWeightDataList(startDate, endDate)
        }
    }


    fun refreshWeightData() {
        viewModelScope.launch {
            val dateRange = _dateRangeState.value
            _weightDataState.value = fetchWeightDataList(dateRange.startDate, dateRange.endDate)
        }
    }

    fun filterWeightDataByRecordId(recordId: Int): List<WeightData> {
        return weightDataState.value.filter { it.recordId == recordId }
    }
    suspend fun fetchWeightDataList(startDate: String, endDate: String): List<WeightData> {
        val url = "$serverUrl/selectBodyData"
        val gson = Gson()
        val jsonObject = JsonObject().apply {
            addProperty("userId", 2)
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
