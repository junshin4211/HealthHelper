package com.example.healthhelper.dietary.repository

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.healthhelper.R
import com.example.healthhelper.dietary.dataclasses.vo.NutritionInfoPairVO
import com.example.healthhelper.dietary.dataclasses.vo.NutritionInfoVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object NutritionInfoRepository {
    private val _dataFlow : MutableStateFlow<NutritionInfoVO>
        @Composable
        get() = MutableStateFlow(fetchData())
    val dataFlow: StateFlow<NutritionInfoVO>
        @Composable
        get() = _dataFlow.asStateFlow()

    @Composable
    fun fetchData(): NutritionInfoVO {
        return NutritionInfoVO(
            fat = NutritionInfoPairVO(
                name = stringResource(R.string.fat),
                value = 1.0,
                unit = stringResource(R.string.grams),
            ),
            carbon = NutritionInfoPairVO(
                name = stringResource(R.string.carb),
                value = 1.0,
                unit = "${stringResource(R.string.milli)}${stringResource(R.string.grams)}",
            ),
            protein = NutritionInfoPairVO(
                name = stringResource(R.string.protein),
                value = 1.0,
                unit = stringResource(R.string.grams),
            ),
            fiber = NutritionInfoPairVO(
                name = stringResource(R.string.fiber),
                value = 1.0,
                unit = "${stringResource(R.string.milli)}${stringResource(R.string.grams)}",
            ),
            sugar = NutritionInfoPairVO(
                name = stringResource(R.string.sugar),
                value = 1.0,
                unit = stringResource(R.string.grams),
            ),
            sodium = NutritionInfoPairVO(
                name = stringResource(R.string.sodium),
                value = 1.0,
                unit = "${stringResource(R.string.milli)}${stringResource(R.string.grams)}",
            ),
            calories = NutritionInfoPairVO(
                name = stringResource(R.string.calories),
                value = 1.0,
                unit = stringResource(R.string.cals),
            ),
        )
    }
}