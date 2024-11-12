package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.DiaryVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.sql.Date

object DiaryRepository {
    private val _dataFlow = MutableStateFlow(DiaryVO())
    val dataFlow: StateFlow<DiaryVO>
        get() = _dataFlow.asStateFlow()

    fun setData(newData: DiaryVO) {
        _dataFlow.update { newData }
        DiaryDescriptionRepository.setDiaryId(newData.diaryID)
    }

    fun setUserId(userId:Int){
        _dataFlow.value.userID = userId
    }

    fun setDiaryId(diaryId:Int){
        _dataFlow.value.diaryID = diaryId
    }

    fun setCreateDate(createDate: Date){
        _dataFlow.value.createDate = createDate
    }
}