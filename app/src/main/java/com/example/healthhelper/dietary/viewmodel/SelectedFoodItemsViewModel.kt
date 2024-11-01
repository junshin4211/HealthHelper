package com.example.healthhelper.dietary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import com.example.healthhelper.dietary.repository.SelectedFoodItemsRepository
import kotlinx.coroutines.flow.StateFlow

class SelectedFoodItemsViewModel: ViewModel(){
    private val repository = SelectedFoodItemsRepository
    val data: StateFlow<List<SelectedFoodItemVO>> = repository.dataFlow
}