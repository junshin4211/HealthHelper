package com.example.healthhelper.dietary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.dao.FoodDiaryDao
import com.example.healthhelper.dietary.repository.FoodDiaryRepository
import kotlinx.coroutines.flow.StateFlow

class FoodDiaryViewModel: ViewModel()  {
    private val repository = FoodDiaryRepository
    val data: StateFlow<MutableList<FoodDiaryDao>> = repository.dataFlow
}