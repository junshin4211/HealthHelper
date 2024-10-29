package com.example.healthhelper.dietary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.TimeRange
import com.example.healthhelper.dietary.repository.MealsTimeRangeCategoryRepository
import kotlinx.coroutines.flow.StateFlow

class MealsTimeRangeCategoryViewModel: ViewModel(){
    private val repository = MealsTimeRangeCategoryRepository
    val data: StateFlow<MutableList<TimeRange>> = repository.dataFlow
}