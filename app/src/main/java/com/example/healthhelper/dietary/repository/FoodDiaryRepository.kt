package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.FoodDiaryVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.sql.Date

object FoodDiaryRepository {
    private val _dataFlow = MutableStateFlow(FoodDiaryVO())
    val dataFlow: StateFlow<FoodDiaryVO>
        get() = _dataFlow.asStateFlow()

    fun setData(newData: FoodDiaryVO) {
        _dataFlow.update { newData }
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