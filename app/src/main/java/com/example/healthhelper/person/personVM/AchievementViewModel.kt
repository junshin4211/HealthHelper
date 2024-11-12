package com.example.healthhelper.person.personVM

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.person.model.Achievement
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

class AchievementViewModel : ViewModel() {
    private val _achievementState = MutableStateFlow<List<Achievement>>(emptyList())
    val achievementState: StateFlow<List<Achievement>> = _achievementState.asStateFlow()
    val userId = UserManager.getUser().userId

    init {
        viewModelScope.launch {
            _achievementState.value = fetchAchievementList(userId)
        }
    }

    fun getAchievementsByType(typeId: List<Int>): List<Achievement> {
        return achievementState.value.filter { it.aTypeId in typeId }
    }

    fun refreshFetchAchievementList(){
        viewModelScope.launch {
            _achievementState.value = fetchAchievementList(userId)
        }
    }

    suspend fun fetchAchievementList(userId: Int): List<Achievement> {
        val url = "${serverUrl}/achievement/getlist"
        val gson = Gson()
        val jsonObject = JsonObject()
        jsonObject.addProperty("userId", userId)
        return try {
            val result = httpPost(url, jsonObject.toString())
            val collectionType = object : TypeToken<List<Achievement>>() {}.type
            gson.fromJson(result, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching achievements from $url: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun insertAchievement(userId: Int) {
        val url = "${serverUrl}/insertAchievement"
        val gson = Gson()
        val jsonObject = JsonObject()
        jsonObject.addProperty("userId", userId)

        val result = httpPost(url, jsonObject.toString())
        val response = gson.fromJson(result, JsonObject::class.java)
        Log.d("dataout", response.get("result").toString())
//        if (response.get("result").asBoolean) {
            refreshFetchAchievementList()
//        }
        Log.d("dataIn", response.get("result").toString())
    }
}