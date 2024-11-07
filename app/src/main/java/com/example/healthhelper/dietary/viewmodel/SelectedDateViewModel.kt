package com.example.healthhelper.dietary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.SelectedDateVO
import com.example.healthhelper.dietary.repository.SelectedDateRepository
import kotlinx.coroutines.flow.StateFlow

class SelectedDateViewModel: ViewModel() {
    val TAG = "tag_SelectedDateViewModel"

    private val repository = SelectedDateRepository
    val selectedDate: StateFlow<SelectedDateVO> = repository.dataFlow
}