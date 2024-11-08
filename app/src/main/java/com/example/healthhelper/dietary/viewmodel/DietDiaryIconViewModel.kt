package com.example.healthhelper.dietary.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.DietDiaryDescriptionVO
import com.example.healthhelper.dietary.gson.gson
import com.example.healthhelper.dietary.repository.DietDiaryDescriptionRepository
import com.example.healthhelper.dietary.servlet.url.DietDiaryUrl
import com.example.healthhelper.web.httpPost
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.StateFlow

class DietDiaryIconViewModel: ViewModel(){
    private val repository = DietDiaryDescriptionRepository
    val data: StateFlow<DietDiaryDescriptionVO> = repository.dataFlow

    suspend fun tryToInsert(
        dietDiaryDescriptionVO: DietDiaryDescriptionVO,
    ):Int{
        val url = DietDiaryUrl.tryToInsertDiaryDescription
        return try {
            val result = httpPost(url, gson.toJson(dietDiaryDescriptionVO))
            val collectionType = object : TypeToken<Int>() {}.type
            gson.fromJson(result, collectionType) ?: 0
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            0
        }
    }
}