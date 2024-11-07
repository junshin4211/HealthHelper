package com.example.healthhelper.dietary.repository

import com.example.healthhelper.R
import com.example.healthhelper.dietary.dataclasses.vo.MealsOptionVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object MealsOptionRepository {

    val TAG = "tag_MealsOptionRepository"

    private val _dataFlow: MutableStateFlow<List<MealsOptionVO>> = MutableStateFlow(fetchData())
    val dataFlow: StateFlow<List<MealsOptionVO>>
        get() = _dataFlow.asStateFlow()

    private val _selectedDataFlow: MutableStateFlow<MealsOptionVO> = MutableStateFlow(fetchData()[0])
    val selectedDataFlow: StateFlow<MealsOptionVO>
        get() = _selectedDataFlow.asStateFlow()

    fun setSelectedData(newData:MealsOptionVO){
        _selectedDataFlow.update { newData }
    }

    fun fetchData(): List<MealsOptionVO> {
        return listOf(
            MealsOptionVO(
                R.drawable.breakfast_leading_icon,
                R.string.breakfast,
                R.string.breakfast_name,
            ),
            MealsOptionVO(
                R.drawable.lunch_leading_icon,
                R.string.lunch,
                R.string.lunch_name,
            ),
            MealsOptionVO(
                R.drawable.dinner_leading_icon,
                R.string.dinner,
                R.string.dinner_name,
            ),
            MealsOptionVO(
                R.drawable.supper_leading_icon,
                R.string.supper,
                R.string.supper_name,
            ),
        )
    }
}