package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.DiaryInfoUpdatorVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object DiaryInfoUpdatorRepository {
    private val _dataFlow = MutableStateFlow(DiaryInfoUpdatorVO())
    val dataFlow: StateFlow<DiaryInfoUpdatorVO> = _dataFlow.asStateFlow()

    fun setData(newData: DiaryInfoUpdatorVO) {
        _dataFlow.update { newData }
    }

    fun setUserId(userId:Int){
        _dataFlow.value.userID = userId
    }
    fun setDiaryId(diaryId:Int){
        _dataFlow.value.diaryID = diaryId
    }
}