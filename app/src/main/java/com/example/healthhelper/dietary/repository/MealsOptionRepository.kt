package com.example.healthhelper.dietary.repository

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.healthhelper.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object MealsOptionRepository {
    private val _dataFlow : MutableStateFlow<MutableList<String>>
        @Composable
        get() = MutableStateFlow<MutableList<String>>(fetchMealsOption().toMutableList())
    val dataFlow: StateFlow<MutableList<String>>
        @Composable
        get() = _dataFlow.asStateFlow()

    @Composable
    fun fetchMealsOption():List<String>{
        return listOf(
            stringResource(R.string.breakfast),
            stringResource(R.string.lunch),
            stringResource(R.string.dinner),
            stringResource(R.string.supper),
        )
    }
}