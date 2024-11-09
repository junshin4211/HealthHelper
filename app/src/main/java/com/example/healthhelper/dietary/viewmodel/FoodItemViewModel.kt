package com.example.healthhelper.dietary.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.FoodItemVO
import com.example.healthhelper.dietary.gson.gson
import com.example.healthhelper.dietary.repository.FoodItemRepository
import com.example.healthhelper.dietary.servlet.url.DietDiaryUrl
import com.example.healthhelper.web.httpPost
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.StateFlow

class FoodItemViewModel:ViewModel() {
    private val repository = FoodItemRepository
    val data: StateFlow<List<FoodItemVO>> = repository.datasFlow

    val selectedData: StateFlow<FoodItemVO> = repository.selectedDataFlow

    suspend fun updateMealCategoryID(
        foodItemVO: FoodItemVO,
    ):Int{
        val url = DietDiaryUrl.updateMealCategoryIDUrl
        return try {
            val result = httpPost(url, gson.toJson(foodItemVO))
            val collectionType = object : TypeToken<Int>() {}.type
            gson.fromJson(result, collectionType) ?: 0
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            0
        }
    }

    suspend fun tryToInsertFoodItem(
        foodItemVO: FoodItemVO,
    ):Int {
        val url = DietDiaryUrl.tryToInsertFoodItemUrl
        return try {
            val result = httpPost(url, gson.toJson(foodItemVO))
            val collectionType = object : TypeToken<Int>() {}.type
            gson.fromJson(result, collectionType) ?: 0
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            0
        }
    }

    suspend fun selectFoodItemByDiaryIdAndMealCategoryId(
        foodItemVO: FoodItemVO,
    ):List<FoodItemVO>{
        val url = DietDiaryUrl.selectFoodItemByDiaryIdAndMealCategoryIdUrl
        return try {
            val result = httpPost(url, gson.toJson(foodItemVO))
            val collectionType = object : TypeToken<FoodItemVO>() {}.type
            gson.fromJson(result, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            emptyList()
        }
    }
}