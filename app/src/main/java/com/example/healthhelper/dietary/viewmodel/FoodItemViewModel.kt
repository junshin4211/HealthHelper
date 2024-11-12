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
    val TAG = "tag_FoodItemViewModel"

    private val repository = FoodItemRepository
    val data: StateFlow<List<FoodItemVO>> = repository.datasFlow

    val selectedData: StateFlow<FoodItemVO> = repository.selectedDataFlow

    suspend fun updateFoodItemByDiaryIdAndFoodId(
        foodItemVO: FoodItemVO,
    ):Int{
        val url = DietDiaryUrl.updateFoodItemByDiaryIdAndFoodId
        return try {
            val result = httpPost(url, gson.toJson(foodItemVO))
            val collectionType = object : TypeToken<Int>() {}.type
            gson.fromJson(result, collectionType) ?: -1
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            -1
        }
    }

    suspend fun tryToInsertFoodItem(
        foodItemVO: FoodItemVO,
    ):Int {
        val url = DietDiaryUrl.tryToInsertFoodItemUrl
        Log.e(TAG,"In FoodItemViewModel class, tryToInsertFoodItem method. foodItemVO:${foodItemVO}")
        return try {
            val result = httpPost(url, gson.toJson(foodItemVO))
            val collectionType = object : TypeToken<Int>() {}.type
            gson.fromJson(result, collectionType) ?: -1
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            -1
        }
    }

    suspend fun selectFoodItemByDiaryIdAndMealCategoryId(
        foodItemVO: FoodItemVO,
    ):List<FoodItemVO>{
        val url = DietDiaryUrl.selectFoodItemByDiaryIdAndMealCategoryIdUrl
        return try {
            val result = httpPost(url, gson.toJson(foodItemVO))
            val collectionType = object : TypeToken<List<FoodItemVO>>() {}.type
            gson.fromJson(result, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun selectFoodItemByDiaryId(
        foodItemVO: FoodItemVO,
    ):List<FoodItemVO>{
        val url = DietDiaryUrl.selectFoodItemByDiaryIdUrl
        return try {
            val result = httpPost(url, gson.toJson(foodItemVO))
            val collectionType = object : TypeToken<List<FoodItemVO>>() {}.type
            gson.fromJson(result, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun deleteFoodItemByDiaryIdAndFoodId(
        foodItemVO: FoodItemVO,
    ):Int{
        val url = DietDiaryUrl.deleteFoodItemByDiaryIdAndFoodIdUrl
        return try {
            val result = httpPost(url, gson.toJson(foodItemVO))
            val collectionType = object : TypeToken<Int>() {}.type
            gson.fromJson(result, collectionType) ?: -1
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            -1
        }
    }
}