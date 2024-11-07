package com.example.healthhelper.dietary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.SelectedTimeVO
import com.example.healthhelper.dietary.repository.SelectedTimeRepository
import kotlinx.coroutines.flow.StateFlow

class SelectedTimeViewModel: ViewModel() {
    val TAG = "tag_SelectedTimeViewModel"

    private val repository = SelectedTimeRepository
    val selectedDate: StateFlow<SelectedTimeVO> = repository.dataFlow
}