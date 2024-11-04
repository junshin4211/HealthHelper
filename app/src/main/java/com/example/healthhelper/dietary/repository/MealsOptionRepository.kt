package com.example.healthhelper.dietary.repository

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.healthhelper.R
import com.example.healthhelper.dietary.dataclasses.vo.MealsOptionVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object MealsOptionRepository {

    val TAG = "tag_MealsOptionRepository"

    private val _dataFlow: MutableStateFlow<List<MealsOptionVO>>
        @Composable
        get() = MutableStateFlow(fetchData())
    val dataFlow: StateFlow<List<MealsOptionVO>>
        @Composable
        get() = _dataFlow.asStateFlow()

    private val _selectedDataFlow: MutableStateFlow<MealsOptionVO>
        @Composable
        get() = MutableStateFlow(fetchData()[0])

    val selectedDataFlow: StateFlow<MealsOptionVO>
        @Composable
        get() {
            return _selectedDataFlow.asStateFlow()
        }

    @Composable
    fun setSelectedData(newData:MealsOptionVO){
        _selectedDataFlow.update { newData }
    }

    @Composable
    fun fetchData(): List<MealsOptionVO> {
        return listOf(
            MealsOptionVO(
                R.drawable.breakfast_leading_icon,
                stringResource(R.string.breakfast),
                stringResource(R.string.breakfast_name),
            ),
            MealsOptionVO(
                R.drawable.lunch_leading_icon,
                stringResource(R.string.lunch),
                stringResource(R.string.lunch_name),
            ),
            MealsOptionVO(
                R.drawable.dinner_leading_icon,
                stringResource(R.string.dinner),
                stringResource(R.string.dinner_name),
            ),
            MealsOptionVO(
                R.drawable.supper_leading_icon,
                stringResource(R.string.supper),
                stringResource(R.string.supper_name),
            ),
        )
    }
}