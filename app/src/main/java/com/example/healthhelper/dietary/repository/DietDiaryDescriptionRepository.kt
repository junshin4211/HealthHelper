package com.example.healthhelper.dietary.repository

import android.net.Uri
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

    fun setUri(uri:Uri?){
        _dataFlow.value.uri = uri
    }

    fun setDescription(description:String){
        _dataFlow.value.description.value = description
    }
}