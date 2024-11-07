package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.SelectedTimeVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object SelectedTimeRepository {
    private val _dataFlow = MutableStateFlow(SelectedTimeVO())
    val dataFlow: StateFlow<SelectedTimeVO> = _dataFlow.asStateFlow()

    fun setData(newData: SelectedTimeVO) {
        _dataFlow.update { newData }
    }

    fun setUserId(userId:UInt){
        _dataFlow.value.userId.value = userId.toInt()
    }
}