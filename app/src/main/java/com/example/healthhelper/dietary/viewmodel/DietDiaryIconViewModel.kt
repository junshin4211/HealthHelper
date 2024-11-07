package com.example.healthhelper.dietary.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthhelper.dietary.dataclasses.vo.DietDiaryDescriptionVO
import com.example.healthhelper.dietary.repository.DietDiaryDescriptionRepository
import kotlinx.coroutines.flow.StateFlow

class DietDiaryIconViewModel: ViewModel(){
    private val repository = DietDiaryDescriptionRepository
    val data: StateFlow<DietDiaryDescriptionVO> = repository.dataFlow
}