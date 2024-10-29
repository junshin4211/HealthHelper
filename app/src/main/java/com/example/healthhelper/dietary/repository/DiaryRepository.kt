package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.DiaryVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object DiaryRepository {
    private val _dataFlow = MutableStateFlow<MutableList<DiaryVO>>(mutableListOf<DiaryVO>())
    val dataFlow: StateFlow<MutableList<DiaryVO>> = _dataFlow.asStateFlow()

    fun setData(newData: DiaryVO) {
        _dataFlow.value.clear()
        _dataFlow.value.add(newData)
    }

    fun addData(newData: DiaryVO){
        _dataFlow.value.add(newData)
    }

    fun removeAt(index:Int){
        _dataFlow.value.removeAt(index)
    }
}