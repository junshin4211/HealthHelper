package com.example.healthhelper.dietary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.other.EnterStatusVO
import com.example.healthhelper.dietary.repository.EnterStatusRepository
import kotlinx.coroutines.flow.StateFlow

class EnterStatusViewModel:ViewModel() {
    private val repository = EnterStatusRepository
    val isFirstEnter: StateFlow<EnterStatusVO> = repository.isFirstEnter
}