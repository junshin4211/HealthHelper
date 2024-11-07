package com.example.healthhelper.dietary.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.NutritionInfoVO
import com.example.healthhelper.dietary.repository.NutritionInfoRepository
import kotlinx.coroutines.flow.StateFlow

class NutritionInfoViewModel: ViewModel() {
    private val repository = NutritionInfoRepository
    val data: StateFlow<NutritionInfoVO>
        @Composable
        get() = repository.dataFlow
}