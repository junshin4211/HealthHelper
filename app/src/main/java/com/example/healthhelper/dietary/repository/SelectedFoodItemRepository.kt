package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object SelectedFoodItemRepository {
    private val _dataFlow = MutableStateFlow<SelectedFoodItemVO>(SelectedFoodItemVO(name = "",grams = 0.0))
    val dataFlow: StateFlow<SelectedFoodItemVO> = _dataFlow.asStateFlow()

    fun setData(newData: SelectedFoodItemVO){
        //_dataFlow.value = newData
        _dataFlow.update { newData }
    }
}