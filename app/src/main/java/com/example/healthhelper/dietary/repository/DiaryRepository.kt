package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.dao.DiaryDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object DiaryRepository {
    private val _dataFlow = MutableStateFlow<MutableList<DiaryDao>>(mutableListOf<DiaryDao>())
    val dataFlow: StateFlow<MutableList<DiaryDao>> = _dataFlow.asStateFlow()

    fun setData(newData: DiaryDao) {
        _dataFlow.value.clear()
        _dataFlow.value.add(newData)
    }

    fun addData(newData: DiaryDao){
        _dataFlow.value.add(newData)
    }

    fun removeAt(index:Int){
        _dataFlow.value.removeAt(index)
    }
}