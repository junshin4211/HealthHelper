package com.example.healthhelper.dietary.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.FoodVO
import com.example.healthhelper.dietary.gson.gson
import com.example.healthhelper.dietary.repository.FoodRepository
import com.example.healthhelper.dietary.servlet.url.DietDiaryUrl
import com.example.healthhelper.web.httpPost
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.StateFlow

class FoodViewModel : ViewModel() {
    private val repository = FoodRepository
    val data: StateFlow<FoodVO>
        get() = repository.dataFlow

    suspend fun selectByFoodName(
        foodVO: FoodVO,
    ):List<FoodVO>{
        val url = DietDiaryUrl.selectFoodIdByFoodNameUrl
        return try {
            val dataOut = gson.toJson(foodVO)
            val result = httpPost(url, dataOut)
            val collectionType = object : TypeToken<List<FoodVO>>() {}.type
            gson.fromJson(result, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            emptyList()
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("TAG","onClear")
    }

    suspend fun selectFoodIdByFoodName(
        foodVO: FoodVO,
    ):Int{
        val foodVOs = selectByFoodName(foodVO)
        if(foodVOs.isEmpty()){
            return 1000
        }
        return foodVOs[0].foodID
    }

    suspend fun selectByFoodId(
        foodVO: FoodVO,
    ):List<FoodVO>{
        val url = DietDiaryUrl.selectFoodNameByFoodIdUrl
        return try {
            val dataOut = gson.toJson(foodVO)
            val result = httpPost(url, dataOut)
            val collectionType = object : TypeToken<List<FoodVO>>() {}.type
            gson.fromJson(result, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching food from ${url}: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun selectFoodNameByFoodId(
        foodVO: FoodVO,
    ):String{
        val foodVOs = selectByFoodId(foodVO)
        if(foodVOs.isEmpty()){
            return "大麥仁"
        }
        return foodVOs[0].foodName
    }
}