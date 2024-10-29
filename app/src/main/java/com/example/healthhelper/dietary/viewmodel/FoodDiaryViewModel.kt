package com.example.healthhelper.dietary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.FoodDiaryVO
import com.example.healthhelper.dietary.repository.FoodDiaryRepository
import kotlinx.coroutines.flow.StateFlow

class FoodDiaryViewModel: ViewModel()  {
    private val repository = FoodDiaryRepository
    val data: StateFlow<MutableList<FoodDiaryVO>> = repository.dataFlow
}