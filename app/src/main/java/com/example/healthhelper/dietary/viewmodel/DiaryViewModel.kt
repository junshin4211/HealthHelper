package com.example.healthhelper.dietary.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.DiaryVO
import com.example.healthhelper.dietary.gson.gson
import com.example.healthhelper.dietary.repository.DiaryRepository
import com.example.healthhelper.dietary.servlet.url.DietDiaryUrl
import com.example.healthhelper.web.httpPost
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.StateFlow

class DiaryViewModel: ViewModel() {
    private val repository = DiaryRepository
    val data: StateFlow<MutableList<DiaryVO>> = repository.dataFlow

    suspend fun fetchDataFromWebRequest():List<DiaryVO>{
        val url = DietDiaryUrl.queryByDateUrl
        return try {
            val result = httpPost(url, "")
            val collectionType = object : TypeToken<List<DiaryVO>>() {}.type
            gson.fromJson(result, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            emptyList()
        }
    }
}