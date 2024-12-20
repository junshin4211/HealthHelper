package com.example.healthhelper.dietary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.NutritionVO
import com.example.healthhelper.dietary.repository.NutritionRepository
import kotlinx.coroutines.flow.StateFlow

class NutritionViewModel:ViewModel() {
    private val repository = NutritionRepository
    val data: StateFlow<MutableList<NutritionVO>> = repository.dataFlow
}