package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.FoodVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object FoodRepository {
    private val _dataFlow = MutableStateFlow<FoodVO>(FoodVO())
    val dataFlow: StateFlow<FoodVO>
        get() = _dataFlow.asStateFlow()

    fun setData(newData: FoodVO){
        _dataFlow.update { newData }
    }

    fun setFoodName(foodName:String){
        _dataFlow.value.foodName = foodName
    }
}