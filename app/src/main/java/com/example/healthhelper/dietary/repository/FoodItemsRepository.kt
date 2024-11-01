package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.FoodItemVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object FoodItemsRepository {
    private val _dataFlow = MutableStateFlow<MutableList<FoodItemVO>>(mutableListOf())
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

    fun addData(newData: FoodItemVO){
        _dataFlow.value.add(newData)
    }

    fun addDatas(newDatas: List<FoodItemVO>){
        _dataFlow.value.addAll(newDatas)
    }
}