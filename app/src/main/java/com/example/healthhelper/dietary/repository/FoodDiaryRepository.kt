package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.FoodDiaryVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object FoodDiaryRepository {
    private val _dataFlow = MutableStateFlow<MutableList<FoodDiaryVO>>(mutableListOf<FoodDiaryVO>())
    val dataFlow: StateFlow<MutableList<FoodDiaryVO>> = _dataFlow.asStateFlow()

    fun setData(newData: FoodDiaryVO) {
        _dataFlow.value.clear()
        _dataFlow.value.add(newData)
    }

    fun removeAt(index:Int){
        _dataFlow.value.removeAt(index)
    }
}