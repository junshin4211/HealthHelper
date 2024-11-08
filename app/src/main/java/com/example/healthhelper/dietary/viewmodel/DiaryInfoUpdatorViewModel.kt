package com.example.healthhelper.dietary.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.DiaryInfoUpdatorVO
import com.example.healthhelper.dietary.repository.DiaryInfoUpdatorRepository
import com.example.healthhelper.dietary.servlet.url.DietDiaryUrl
import com.example.healthhelper.dietary.util.gson.GsonForSqlDateAndSqlTime
import com.example.healthhelper.web.httpPost
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.StateFlow

class DiaryInfoUpdatorViewModel: ViewModel() {
    private val repository = DiaryInfoUpdatorRepository
    val data: StateFlow<DiaryInfoUpdatorVO> = repository.dataFlow

    suspend fun updateDiaryInfo(
        diaryInfoUpdatorVO: DiaryInfoUpdatorVO,
    ):Int{
        val url = DietDiaryUrl.updateDiaryInfoUrl
        return try {
            val gson = GsonForSqlDateAndSqlTime.gson
            val dataOut = gson.toJson(diaryInfoUpdatorVO)
            val result = httpPost(url, dataOut)
            val collectionType = object : TypeToken<DiaryInfoUpdatorVO>() {}.type
            gson.fromJson(result, collectionType) ?: -1
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            -1
        }
    }
}