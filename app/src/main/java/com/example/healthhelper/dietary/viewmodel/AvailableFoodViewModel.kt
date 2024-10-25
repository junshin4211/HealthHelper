package com.example.healthhelper.dietary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.repository.AvailableFoodRepository
import kotlinx.coroutines.flow.StateFlow

class AvailableFoodViewModel: ViewModel() {
    private val repository = AvailableFoodRepository
    val data: StateFlow<MutableList<String>> = repository.dataFlow
}