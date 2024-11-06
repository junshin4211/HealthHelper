package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.SelectedDateVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.sql.Date

object SelectedDateRepository {
    private val _dataFlow = MutableStateFlow(SelectedDateVO())
    val dataFlow: StateFlow<SelectedDateVO> = _dataFlow.asStateFlow()

    fun setData(newData: SelectedDateVO) {
        _dataFlow.update { newData }
    }

    fun setUserId(userId:UInt){
        _dataFlow.value.userId.value = userId.toInt()
    }

    fun setDate(selectedDate:Date){
        _dataFlow.value.selectedDate.value = selectedDate
    }
}