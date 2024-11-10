package com.example.healthhelper.dietary.repository

import android.util.Log
import com.example.healthhelper.dietary.dataclasses.vo.FoodItemVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


object FoodItemRepository {
    val TAG = "tag_MealsOptionRepository"

    private val _datasFlow: MutableStateFlow<MutableList<FoodItemVO>> = MutableStateFlow(mutableListOf())
    val datasFlow: StateFlow<List<FoodItemVO>>
        get() = _datasFlow.asStateFlow()

    private val _selectedDataFlow: MutableStateFlow<FoodItemVO> = MutableStateFlow(FoodItemVO(2,2, grams = 2.0))
    val selectedDataFlow: StateFlow<FoodItemVO>
        get() = _selectedDataFlow.asStateFlow()

    fun setDatas(newDatas:List<FoodItemVO>){
        Log.e(TAG,"~".repeat(50))
        Log.e(TAG,"In setDatas method,newDatas:${newDatas}")
        _datasFlow.value = newDatas.toMutableList()
        Log.e(TAG,"In setDatas method,_datasFlow.value:${_datasFlow.value}")
        Log.e(TAG,"~".repeat(50))
    }

    fun setSelectedDiaryId(diaryId:Int){
        _selectedDataFlow.value.diaryID = diaryId
    }

    fun setSelectedFoodId(foodID:Int){
        _selectedDataFlow.value.foodID = foodID
    }

    fun setSelectedMealCategoryId(mealCategoryId:Int){
        _selectedDataFlow.value.mealCategoryID = mealCategoryId
    }

    fun setSelectedGrams(grams:Double){
        _selectedDataFlow.value.grams = grams
    }
}