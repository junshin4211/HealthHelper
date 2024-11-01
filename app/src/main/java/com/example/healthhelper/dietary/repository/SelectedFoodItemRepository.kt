package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object SelectedFoodItemRepository {
    private val _dataFlow = MutableStateFlow<SelectedFoodItemVO>(fetchData())
    val dataFlow: StateFlow<SelectedFoodItemVO> = _dataFlow.asStateFlow()

    private fun fetchData():SelectedFoodItemVO{
        return SelectedFoodItemVO("Apple")
    }
    fun setData(newData: SelectedFoodItemVO){
        _dataFlow.update { newData }
    }
}