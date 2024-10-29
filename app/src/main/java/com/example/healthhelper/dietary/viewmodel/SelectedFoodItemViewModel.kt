package com.example.healthhelper.dietary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import com.example.healthhelper.dietary.repository.SelectedFoodItemRepository
import kotlinx.coroutines.flow.StateFlow

class SelectedFoodItemViewModel: ViewModel(){
    private val repository = SelectedFoodItemRepository
    val data: StateFlow<SelectedFoodItemVO> = repository.dataFlow
}