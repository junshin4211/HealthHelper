package com.example.healthhelper.dietary.repository

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.healthhelper.R
import com.example.healthhelper.dietary.dataclasses.vo.FoodDiaryVO
import com.example.healthhelper.dietary.dataclasses.vo.NutritionInfoPairVO
import com.example.healthhelper.dietary.dataclasses.vo.NutritionInfoVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object NutritionInfoRepository {
    private val _dataFlow : MutableStateFlow<NutritionInfoVO> = MutableStateFlow(fetchData())
    val dataFlow: StateFlow<NutritionInfoVO>
        get() = _dataFlow.asStateFlow()

    fun fetchData(): NutritionInfoVO {
        return NutritionInfoVO(
            fat = mutableStateOf(NutritionInfoPairVO(
                nameResId = R.string.fat,
                amount = mutableDoubleStateOf(1.0),
                unitResId = R.string.grams,
            )),
            carbon = mutableStateOf(NutritionInfoPairVO(
                nameResId = R.string.carb,
                amount = mutableDoubleStateOf(1.0),
                unitResId = R.string.grams,
            )),
            protein = mutableStateOf(NutritionInfoPairVO(
                nameResId = R.string.protein,
                amount = mutableDoubleStateOf(1.0),
                unitResId = R.string.grams,
            )),
            fiber = mutableStateOf(NutritionInfoPairVO(
                nameResId = R.string.fiber,
                amount = mutableDoubleStateOf(1.0),
                unitResId = R.string.grams,
            )),
            sugar = mutableStateOf(NutritionInfoPairVO(
                nameResId = R.string.sugar,
                amount = mutableDoubleStateOf(1.0),
                unitResId = R.string.grams,
            )),
            sodium = mutableStateOf(NutritionInfoPairVO(
                nameResId = R.string.sodium,
                amount = mutableDoubleStateOf(1.0),
                unitResId = R.string.milli_grams,
            )),
            calories = mutableStateOf(NutritionInfoPairVO(
                nameResId = R.string.calories,
                amount = mutableDoubleStateOf(1.0),
                unitResId = R.string.kilo_cals,
            )),
        )
    }

    fun setNutritionInfo(
        foodDiaryVO: FoodDiaryVO,
    ){
        _dataFlow.value.fat.value.amount.value = foodDiaryVO.totalFat
        _dataFlow.value.carbon.value.amount.value = foodDiaryVO.totalCarbon
        _dataFlow.value.protein.value.amount.value = foodDiaryVO.totalProtein
        _dataFlow.value.fiber.value.amount.value = foodDiaryVO.totalProtein
        _dataFlow.value.sugar.value.amount.value = foodDiaryVO.totalSugar
        _dataFlow.value.sodium.value.amount.value = foodDiaryVO.totalSodium
        _dataFlow.value.calories.value.amount.value = foodDiaryVO.totalCalories
    }
}