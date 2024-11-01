package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.FoodItemVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object FoodItemsRepository {
    private val _dataFlow = MutableStateFlow<MutableList<FoodItemVO>>(fetchData())
    val dataFlow: StateFlow<MutableList<FoodItemVO>> = _dataFlow.asStateFlow()

    fun fetchData():MutableList<FoodItemVO>{
        return mutableListOf(
            FoodItemVO(name = "Apple"),
            FoodItemVO(name = "Banana"),
            FoodItemVO(name = "Grape"),
            FoodItemVO(name = "Orange"),
        )
    }
    fun setData(newData: FoodItemVO) {
        _dataFlow.value.clear()
        _dataFlow.value.add(newData)
    }

    fun removeAt(index:Int){
        _dataFlow.value.removeAt(index)
    }
}