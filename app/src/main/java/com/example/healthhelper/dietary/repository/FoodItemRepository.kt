package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.FoodItemVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object FoodItemRepository {
    val TAG = "tag_MealsOptionRepository"

    private val _dataFlow: MutableStateFlow<FoodItemVO> = MutableStateFlow(FoodItemVO(1,1,grams = 1.0))
    val dataFlow: StateFlow<FoodItemVO>
        get() = _dataFlow.asStateFlow()

    fun setData(newData:FoodItemVO){
        _dataFlow.update { newData }
    }
}