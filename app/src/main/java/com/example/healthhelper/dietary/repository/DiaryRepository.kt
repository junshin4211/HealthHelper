package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.DiaryVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object DiaryRepository {
    private val _dataFlow = MutableStateFlow(mutableListOf<DiaryVO>())
    val dataFlow: StateFlow<MutableList<DiaryVO>> = _dataFlow.asStateFlow()

    fun setData(newData: List<DiaryVO>) {
        _dataFlow.value.clear()
        _dataFlow.value.addAll(newData)
    }
}