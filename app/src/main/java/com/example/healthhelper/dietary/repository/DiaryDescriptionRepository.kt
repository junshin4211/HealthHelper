package com.example.healthhelper.dietary.repository

import android.net.Uri
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

    fun setUri(uri:Uri?){
        _dataFlow.value.uri.value = uri
    }

    fun setDescription(description:String){
        _dataFlow.value.description.value = description
    }

    fun clearData(){
        _dataFlow.value.uri.value = null
        _dataFlow.value.description.value = ""
    }
}