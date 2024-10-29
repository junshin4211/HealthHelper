package com.example.healthhelper.dietary.repository

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.healthhelper.R
import com.example.healthhelper.dietary.dataclasses.vo.MealsOptionVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object MealsOptionRepository {
    private val _dataFlow: MutableStateFlow<MutableList<MealsOptionVO>>
        @Composable
        get() = MutableStateFlow<MutableList<MealsOptionVO>>(fetchMealsOption().toMutableList())
    val dataFlow: StateFlow<MutableList<MealsOptionVO>>
        @Composable
        get() = _dataFlow.asStateFlow()

    @Composable
    fun fetchMealsOption(): List<MealsOptionVO> {
        return listOf(
            MealsOptionVO(
                R.drawable.breakfast_leading_icon,
                stringResource(R.string.breakfast),
            ),
            MealsOptionVO(
                R.drawable.lunch_leading_icon,
                stringResource(R.string.lunch),
            ),
            MealsOptionVO(
                R.drawable.dinner_leading_icon,
                stringResource(R.string.dinner),
            ),
            MealsOptionVO(
                R.drawable.supper_leading_icon,
                stringResource(R.string.supper),
            ),
        )
    }
}