package com.example.healthhelper.dietary.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.DiaryDescriptionVO
import com.example.healthhelper.dietary.gson.gson
import com.example.healthhelper.dietary.repository.DiaryDescriptionRepository
import com.example.healthhelper.dietary.servlet.url.DietDiaryUrl
import com.example.healthhelper.web.httpPost
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.StateFlow

class DiaryDescriptionViewModel: ViewModel(){
    private val repository = DiaryDescriptionRepository
    val data: StateFlow<DiaryDescriptionVO> = repository.dataFlow

    suspend fun tryToInsert(
        diaryDescriptionVO: DiaryDescriptionVO,
    ):Int{
        val url = DietDiaryUrl.tryToInsertDiaryDescriptionUrl
        return try {
            val result = httpPost(url, gson.toJson(diaryDescriptionVO))
            val collectionType = object : TypeToken<Int>() {}.type
            gson.fromJson(result, collectionType) ?: -1
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            -1
        }
    }

    suspend fun loadDiaryInfo(
        diaryDescriptionVO: DiaryDescriptionVO,
    ):List<DiaryDescriptionVO>{
        val url = DietDiaryUrl.selectDiaryDescriptionUrl
        return try {
            val result = httpPost(url, gson.toJson(diaryDescriptionVO))
            val collectionType = object : TypeToken<List<DiaryDescriptionVO>>() {}.type
            gson.fromJson(result, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            emptyList()
        }
    }
}