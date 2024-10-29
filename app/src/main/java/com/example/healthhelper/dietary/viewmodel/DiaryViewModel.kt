package com.example.healthhelper.dietary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.DiaryVO
import com.example.healthhelper.dietary.repository.DiaryRepository
import kotlinx.coroutines.flow.StateFlow

class DiaryViewModel: ViewModel() {
    private val repository = DiaryRepository
    val data: StateFlow<MutableList<DiaryVO>> = repository.dataFlow
}