package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.DiaryDescriptionVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object DiaryDescriptionRepository {
    private val _dataFlow = MutableStateFlow(DiaryDescriptionVO(2,2))
    val dataFlow: StateFlow<DiaryDescriptionVO>
        get() = _dataFlow.asStateFlow()

    fun setData(newData:DiaryDescriptionVO){
       _dataFlow.update { newData }
    }

    fun setDiaryId(diaryId:Int){
        _dataFlow.value.diaryID = diaryId
    }

    fun setMealCategoryId(mealCategoryId:Int){
        _dataFlow.value.mealCategoryID = mealCategoryId
    }

    fun setUri(uri : String?) {
        _dataFlow.value.uri = uri
    }
    fun setDescription(description:String){
        _dataFlow.value.description = description
    }

    fun clearData(){
        _dataFlow.value.uri = null
        _dataFlow.value.description = ""
    }
}