package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.NutritionVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object NutritionRepository {
    private val _dataFlow = MutableStateFlow<MutableList<NutritionVO>>(mutableListOf<NutritionVO>())
    val dataFlow: StateFlow<MutableList<NutritionVO>> = _dataFlow.asStateFlow()

    fun setData(newData: NutritionVO) {
        _dataFlow.value.clear()
        _dataFlow.value.add(newData)
    }

    fun removeAt(index:Int){
        _dataFlow.value.removeAt(index)
    }

    fun fetchDefaultEntries(): List<NutritionVO>{
        return listOf(
            NutritionVO(
                fat = 20.0,
                carbon = 10.0,
                protein = 5.0,
                fiber = 2.0,
                sugar = 3.0,
                sodium = 4.0,
                calories = 6.0,
            ),
            NutritionVO(
                fat = 30.0,
                carbon = 20.0,
                protein = 15.0,
                fiber = 23.0,
                sugar = 32.0,
                sodium = 42.0,
                calories = 1.0,
            )
        )
    }
}