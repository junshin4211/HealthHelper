package com.example.healthhelper.dietary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.SelectedMealOptionVO
import com.example.healthhelper.dietary.repository.SelectedMealOptionRepository
import kotlinx.coroutines.flow.StateFlow

class SelectedMealOptionViewModel: ViewModel() {
    private val repository = SelectedMealOptionRepository
    val data: StateFlow<SelectedMealOptionVO> = repository.dataFlow
}