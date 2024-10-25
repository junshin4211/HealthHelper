package com.example.healthhelper.dietary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.dao.DiaryDao
import com.example.healthhelper.dietary.repository.DiaryRepository
import kotlinx.coroutines.flow.StateFlow

class DiaryViewModel: ViewModel() {
    private val repository = DiaryRepository
    val data: StateFlow<MutableList<DiaryDao>> = repository.dataFlow
}