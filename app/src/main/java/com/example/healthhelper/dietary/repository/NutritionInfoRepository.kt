package com.example.healthhelper.dietary.repository

import android.util.Log
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.healthhelper.R
import com.example.healthhelper.dietary.dataclasses.vo.DiaryVO
import com.example.healthhelper.dietary.dataclasses.vo.NutritionInfoPairVO
import com.example.healthhelper.dietary.dataclasses.vo.NutritionInfoVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object NutritionInfoRepository {
    val TAG = "tag_NutritionInfoRepository"

    private val _dataFlow: MutableStateFlow<NutritionInfoVO> = MutableStateFlow(fetchData())
    val dataFlow: StateFlow<NutritionInfoVO>
        get() = _dataFlow.asStateFlow()

    fun fetchData(): NutritionInfoVO {
        return NutritionInfoVO(
            fat = mutableStateOf(
                NutritionInfoPairVO(
                    nameResId = R.string.fat,
                    amount = mutableDoubleStateOf(-1.0),
                    unitResId = R.string.grams,
                )
            ),
            carbon = mutableStateOf(
                NutritionInfoPairVO(
                    nameResId = R.string.carb,
                    amount = mutableDoubleStateOf(-1.0),
                    unitResId = R.string.grams,
                )
            ),
            protein = mutableStateOf(
                NutritionInfoPairVO(
                    nameResId = R.string.protein,
                    amount = mutableDoubleStateOf(-1.0),
                    unitResId = R.string.grams,
                )
            ),
            fiber = mutableStateOf(
                NutritionInfoPairVO(
                    nameResId = R.string.fiber,
                    amount = mutableDoubleStateOf(-1.0),
                    unitResId = R.string.grams,
                )
            ),
            sugar = mutableStateOf(
                NutritionInfoPairVO(
                    nameResId = R.string.sugar,
                    amount = mutableDoubleStateOf(-1.0),
                    unitResId = R.string.grams,
                )
            ),
            sodium = mutableStateOf(
                NutritionInfoPairVO(
                    nameResId = R.string.sodium,
                    amount = mutableDoubleStateOf(-1.0),
                    unitResId = R.string.milli_grams,
                )
            ),
            calories = mutableStateOf(
                NutritionInfoPairVO(
                    nameResId = R.string.calories,
                    amount = mutableDoubleStateOf(-1.0),
                    unitResId = R.string.kilo_cals,
                )
            ),
        )
    }

    /*
    fun setNutritionInfo(
        foodDiaryVO: DiaryVO,
    ) {

        _dataFlow.value.fat.value.amount.value = foodDiaryVO.totalFat
        _dataFlow.value.carbon.value.amount.value = foodDiaryVO.totalCarbon
        _dataFlow.value.protein.value.amount.value = foodDiaryVO.totalProtein
        _dataFlow.value.fiber.value.amount.value = foodDiaryVO.totalProtein
        _dataFlow.value.sugar.value.amount.value = foodDiaryVO.totalSugar
        _dataFlow.value.sodium.value.amount.value = foodDiaryVO.totalSodium
        _dataFlow.value.calories.value.amount.value = foodDiaryVO.totalCalories

    }

     */

    fun setNutritionInfo(
        foodDiaryVO: DiaryVO,
    ){
        val newNutritionInfoVO = NutritionInfoVO(
            fat = mutableStateOf(
                NutritionInfoPairVO(
                    nameResId = R.string.fat,
                    amount = mutableDoubleStateOf(foodDiaryVO.totalFat),
                    unitResId = R.string.grams,
                )
            ),
            carbon = mutableStateOf(
                NutritionInfoPairVO(
                    nameResId = R.string.carb,
                    amount = mutableDoubleStateOf(foodDiaryVO.totalCarbon),
                    unitResId = R.string.grams,
                )
            ),
            protein = mutableStateOf(
                NutritionInfoPairVO(
                    nameResId = R.string.protein,
                    amount = mutableDoubleStateOf(foodDiaryVO.totalProtein),
                    unitResId = R.string.grams,
                )
            ),
            fiber = mutableStateOf(
                NutritionInfoPairVO(
                    nameResId = R.string.fiber,
                    amount = mutableDoubleStateOf(foodDiaryVO.totalFiber),
                    unitResId = R.string.grams,
                )
            ),
            sugar = mutableStateOf(
                NutritionInfoPairVO(
                    nameResId = R.string.sugar,
                    amount = mutableDoubleStateOf(foodDiaryVO.totalSugar),
                    unitResId = R.string.grams,
                )
            ),
            sodium = mutableStateOf(
                NutritionInfoPairVO(
                    nameResId = R.string.sodium,
                    amount = mutableDoubleStateOf(foodDiaryVO.totalSodium),
                    unitResId = R.string.milli_grams,
                )
            ),
            calories = mutableStateOf(
                NutritionInfoPairVO(
                    nameResId = R.string.calories,
                    amount = mutableDoubleStateOf(foodDiaryVO.totalCalories),
                    unitResId = R.string.kilo_cals,
                )
            )
        )

        _dataFlow.update { newNutritionInfoVO }
    }

    fun isValidNutritionInfo(nutritionInfoVO: NutritionInfoVO): Boolean {
        Log.e(TAG,"In NutritionInfoRepository object, isValidNutritionInfo function. nutritionInfoVO:${nutritionInfoVO}")
        val retValue = ( nutritionInfoVO.fat.value.amount.value > 0 &&
                nutritionInfoVO.carbon.value.amount.value > 0 &&
                nutritionInfoVO.protein.value.amount.value > 0 &&
                nutritionInfoVO.fiber.value.amount.value > 0 &&
                nutritionInfoVO.sugar.value.amount.value > 0 &&
                nutritionInfoVO.sodium.value.amount.value > 0 &&
                nutritionInfoVO.calories.value.amount.value > 0 )
        return retValue
    }
}