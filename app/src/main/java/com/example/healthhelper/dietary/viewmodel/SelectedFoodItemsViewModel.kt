package com.example.healthhelper.dietary.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.FoodNameVO
import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import com.example.healthhelper.dietary.gson.gson
import com.example.healthhelper.dietary.repository.SelectedFoodItemsRepository
import com.example.healthhelper.dietary.servlet.url.DietDiaryUrl
import com.example.healthhelper.web.httpPost
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.StateFlow

class SelectedFoodItemsViewModel : ViewModel() {
    val TAG = "tag_SelectedFoodItemsViewModel"

    private val repository = SelectedFoodItemsRepository
    val data: StateFlow<List<SelectedFoodItemVO>> = repository.dataFlow
    val selectedData: StateFlow<SelectedFoodItemVO> = repository.selectedDataFlow

    suspend fun fetchDataFromWebRequest():List<FoodNameVO>{
        val url = DietDiaryUrl.listAvailableFoodNameUrl
        return try {
            val result = httpPost(url, "")
            val collectionType = object : TypeToken<List<FoodNameVO>>() {}.type
            gson.fromJson(result, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            emptyList()
        }
    }
}

