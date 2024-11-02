package com.example.healthhelper.dietary.repository

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.healthhelper.R
import com.example.healthhelper.dietary.dataclasses.vo.SelectedMealOptionVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object SelectedMealOptionRepository {
    private val _dataFlow : MutableStateFlow<SelectedMealOptionVO>
        @Composable
        get() = MutableStateFlow<SelectedMealOptionVO>(fetchData())
    val dataFlow: StateFlow<SelectedMealOptionVO>
        @Composable
        get() = _dataFlow.asStateFlow()

    @Composable
    fun fetchData(): SelectedMealOptionVO {
        return SelectedMealOptionVO( name = stringResource(R.string.breakfast),)
    }

    @SuppressLint("StateFlowValueCalledInComposition")
    @Composable
    fun setData(newData:SelectedMealOptionVO){
        _dataFlow.value = newData
    }
}