package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.dao.FoodDiaryDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object FoodDiaryRepository {
    private val _dataFlow = MutableStateFlow<MutableList<FoodDiaryDao>>(mutableListOf<FoodDiaryDao>())
    val dataFlow: StateFlow<MutableList<FoodDiaryDao>> = _dataFlow.asStateFlow()

    fun setData(newData: FoodDiaryDao) {
        _dataFlow.value.clear()
        _dataFlow.value.add(newData)
    }

    fun removeAt(index:Int){
        _dataFlow.value.removeAt(index)
    }
}