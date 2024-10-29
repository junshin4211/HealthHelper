package com.example.healthhelper.dietary.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object AvailableFoodRepository {
    private val _dataFlow = MutableStateFlow<MutableList<String>>(mutableListOf<String>())
    val dataFlow: StateFlow<MutableList<String>> = _dataFlow.asStateFlow()

    fun setData(newData: String) {
        _dataFlow.value.clear()
        _dataFlow.value.add(newData)
    }

    fun removeAt(index:Int){
        _dataFlow.value.removeAt(index)
    }

    fun fetchDataFromDatabase(){

    }
}