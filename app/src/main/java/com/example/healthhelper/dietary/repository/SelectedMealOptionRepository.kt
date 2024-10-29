package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.SelectedMealOptionVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object SelectedMealOptionRepository {
    private val _dataFlow = MutableStateFlow<SelectedMealOptionVO>(SelectedMealOptionVO(name = ""))
    val dataFlow: StateFlow<SelectedMealOptionVO> = _dataFlow.asStateFlow()

    fun setData(newData:SelectedMealOptionVO){
        _dataFlow.value = newData
    }
}