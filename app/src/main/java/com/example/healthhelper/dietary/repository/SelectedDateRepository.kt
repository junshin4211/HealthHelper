package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.SelectedDateVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object SelectedDateRepository {
    private val _dataFlow = MutableStateFlow(SelectedDateVO())
    val dataFlow: StateFlow<SelectedDateVO> = _dataFlow.asStateFlow()

    fun setData(newData: SelectedDateVO) {
        _dataFlow.value = newData
    }
}