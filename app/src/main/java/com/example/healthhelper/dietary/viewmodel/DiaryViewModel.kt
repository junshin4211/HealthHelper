package com.example.healthhelper.dietary.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.DiaryVO
import com.example.healthhelper.dietary.dataclasses.vo.SelectedDateVO
import com.example.healthhelper.dietary.repository.DiaryRepository
import com.example.healthhelper.dietary.servlet.url.DietDiaryUrl
import com.example.healthhelper.dietary.util.gson.GsonForSqlDateAndSqlTime
import com.example.healthhelper.web.httpPost
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.StateFlow

class DiaryViewModel: ViewModel() {
    private val repository = DiaryRepository
    val data: StateFlow<MutableList<DiaryVO>> = repository.dataFlow

    suspend fun getDiaryIdByUserIdAndDate(
        selectedDateVO: SelectedDateVO,
    ):List<DiaryVO>{
        val url = DietDiaryUrl.queryByDateUrl
        return try {
            val gson = GsonForSqlDateAndSqlTime.gson
            val dataOut = gson.toJson(selectedDateVO)
            val result = httpPost(url, dataOut)
            val collectionType = object : TypeToken<List<DiaryVO>>() {}.type
            gson.fromJson(result, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            emptyList()
        }
    }
}