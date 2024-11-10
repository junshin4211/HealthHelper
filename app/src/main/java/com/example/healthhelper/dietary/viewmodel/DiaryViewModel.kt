package com.example.healthhelper.dietary.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.DiaryVO
import com.example.healthhelper.dietary.repository.DiaryRepository
import com.example.healthhelper.dietary.servlet.url.DietDiaryUrl
import com.example.healthhelper.dietary.util.gson.GsonForSqlDateAndSqlTime
import com.example.healthhelper.web.httpPost
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.StateFlow

class DiaryViewModel: ViewModel() {
    private val repository = DiaryRepository
    val data: StateFlow<DiaryVO> = repository.dataFlow

    suspend fun selectDiaryByUserIdAndDate(
        diaryVO: DiaryVO,
    ):List<DiaryVO>{
        val url = DietDiaryUrl.selectDiaryByUserIdAndDateUrl
        return try {
            val gson = GsonForSqlDateAndSqlTime.gson
            val dataOut = gson.toJson(diaryVO)
            val result = httpPost(url, dataOut)
            val collectionType = object : TypeToken<List<DiaryVO>>() {}.type
            gson.fromJson(result, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun insertDiary(
        diaryVO: DiaryVO,
    ):List<DiaryVO>{
        val url = DietDiaryUrl.insertDietDiaryUrl
        return try {
            val gson = GsonForSqlDateAndSqlTime.gson
            val dataOut = gson.toJson(diaryVO)
            val result = httpPost(url, dataOut)
            val collectionType = object : TypeToken<List<DiaryVO>>() {}.type
            gson.fromJson(result, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun updateDiaryInfo(
        diaryVO: DiaryVO,
    ):Int{
        val url = DietDiaryUrl.updateDiaryInfoUrl
        return try {
            val gson = GsonForSqlDateAndSqlTime.gson
            val dataOut = gson.toJson(diaryVO)
            val result = httpPost(url, dataOut)
            val collectionType = object : TypeToken<Int>() {}.type
            gson.fromJson(result, collectionType) ?: -1
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            -1
        }
    }
}