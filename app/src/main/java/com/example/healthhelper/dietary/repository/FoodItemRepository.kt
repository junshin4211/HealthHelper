package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.FoodItemVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object FoodItemRepository {
    val TAG = "tag_MealsOptionRepository"

    private val _datasFlow: MutableStateFlow<MutableList<FoodItemVO>> = MutableStateFlow(mutableListOf())
    val datasFlow: StateFlow<List<FoodItemVO>>
        get() = _datasFlow.asStateFlow()

    private val _selectedDataFlow: MutableStateFlow<FoodItemVO> = MutableStateFlow(FoodItemVO(2,2, grams = 2.0))
    val selectedDataFlow: StateFlow<FoodItemVO>
        get() = _selectedDataFlow.asStateFlow()

    fun setDatas(newDatas:List<FoodItemVO>){
        _datasFlow.update { newDatas.toMutableList() }
    }

    fun setSelectedDiaryId(diaryId:Int){
        _selectedDataFlow.value.diaryID = diaryId
    }

    fun setSelectedFoodId(foodId:Int){
        _selectedDataFlow.value.foodID = foodId
    }

    fun setSelectedMealCategoryId(mealCategoryId:Int){
        _selectedDataFlow.value.mealCategoryID = mealCategoryId
    }

    fun setSelectedGrams(grams:Double){
        _selectedDataFlow.value.grams = grams
    }
}