package com.example.healthhelper.dietary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.FoodItemVO
import com.example.healthhelper.dietary.repository.FoodItemsRepository
import kotlinx.coroutines.flow.StateFlow

class FoodItemsViewModel: ViewModel() {
    private val repository = FoodItemsRepository
    val data: StateFlow<MutableList<FoodItemVO>> = repository.dataFlow
}