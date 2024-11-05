package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.SelectedTimeVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object SelectedTimeRepository {
    private val _dataFlow = MutableStateFlow(SelectedTimeVO())
    val dataFlow: StateFlow<SelectedTimeVO> = _dataFlow.asStateFlow()

    fun setData(newData: SelectedTimeVO) {
        _dataFlow.value = newData
    }
}