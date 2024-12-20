package com.example.healthhelper.dietary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.MealsOptionVO
import com.example.healthhelper.dietary.repository.MealsOptionRepository
import kotlinx.coroutines.flow.StateFlow

class MealsOptionViewModel: ViewModel(){
    private val repository = MealsOptionRepository
    val data: StateFlow<List<MealsOptionVO>>
        get() = repository.dataFlow

    val selectedData: StateFlow<MealsOptionVO>
        get() = repository.selectedDataFlow
}