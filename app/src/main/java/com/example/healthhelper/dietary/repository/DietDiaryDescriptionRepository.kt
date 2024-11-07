package com.example.healthhelper.dietary.repository

import com.example.healthhelper.R
import com.example.healthhelper.dietary.dataclasses.vo.DietDiaryDescriptionVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object DietDiaryDescriptionRepository {
    private val _dataFlow = MutableStateFlow<DietDiaryDescriptionVO>(DietDiaryDescriptionVO(R.drawable.postpic))
    val dataFlow: StateFlow<DietDiaryDescriptionVO>
        get() = _dataFlow.asStateFlow()

    fun setData(newData:DietDiaryDescriptionVO){
        _dataFlow.update { newData }
    }

    fun setIconResId(iconResId:Int){
        _dataFlow.value.iconResId = iconResId
    }

    fun setDescription(description:String){
        _dataFlow.value.description.value = description
    }
}