package com.example.healthhelper.dietary.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.FoodDiaryVO
import com.example.healthhelper.dietary.gson.gson
import com.example.healthhelper.dietary.repository.FoodDiaryRepository
import com.example.healthhelper.dietary.servlet.url.DietDiaryUrl
import com.example.healthhelper.dietary.util.gson.GsonForSqlDateAndSqlTime
import com.example.healthhelper.web.httpPost
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.StateFlow

class FoodDiaryViewModel: ViewModel()  {
    val TAG = "tag_FoodDiaryViewModel"

    private val repository = FoodDiaryRepository
    val data: StateFlow<FoodDiaryVO> = repository.dataFlow

    suspend fun insertDietDiary(
        foodDiaryVO: FoodDiaryVO,
    ):Int{
        val url = DietDiaryUrl.insertDietDiaryUrl
        return try {
            val result = httpPost(url, gson.toJson(foodDiaryVO))
            val collectionType = object : TypeToken<Int>() {}.type
            gson.fromJson(result, collectionType) ?: -1
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            -1
        }
    }

    suspend fun selectByUserIdAndCreateDate(
        foodDiaryVO: FoodDiaryVO,
    ):List<FoodDiaryVO>{
        val url = DietDiaryUrl.selectByUserIdAndCreateDateUrl
        Log.e(TAG,"selectByUserIdAndCreateDate function was called. foodDiaryVO:${foodDiaryVO}")

        return try {
            val gson = GsonForSqlDateAndSqlTime.gson
            val result = httpPost(url, gson.toJson(foodDiaryVO))
            val collectionType = object : TypeToken<List<FoodDiaryVO>>() {}.type
            gson.fromJson(result, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            emptyList()
        }
    }
}