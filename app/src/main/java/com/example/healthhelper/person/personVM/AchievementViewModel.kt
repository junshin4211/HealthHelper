package com.example.healthhelper.person.personVM

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.person.model.Achievement
import com.example.healthhelper.web.httpPost
import com.example.healthhelper.web.serverUrl
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AchievementViewModel : ViewModel() {
    private val _achievementState = MutableStateFlow<List<Achievement>>(emptyList())
    val achievementState: StateFlow<List<Achievement>> = _achievementState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val achievements = fetchAchievementList()
                _achievementState.value = achievements
            } catch (e: Exception) {
                Log.e("Init Error", "Failed to initialize achievements", e)
            }
        }
    }

    fun getAchievementsByType(typeId: List<Int>): List<Achievement> {
        return achievementState.value.filter { it.aTypeId in typeId }
    }

    private suspend fun fetchAchievementList(): List<Achievement> {
        val url = "${serverUrl}/achievement/getlist"
        val gson = Gson()

        return try {
            val result = httpPost(url, "")
            val collectionType = object : TypeToken<List<Achievement>>() {}.type
            gson.fromJson(result, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching achievements from $url: ${e.message}", e)
            emptyList()
        }
    }
}