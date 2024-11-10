package com.example.healthhelper.dietary.repository

import androidx.compose.runtime.mutableStateOf
import com.example.healthhelper.dietary.dataclasses.vo.other.EnterStatusVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object EnterStatusRepository {
    private val _isFirstEnter: MutableStateFlow<EnterStatusVO> = MutableStateFlow(EnterStatusVO(mutableStateOf(true)))
    val isFirstEnter: StateFlow<EnterStatusVO> = _isFirstEnter.asStateFlow()

    fun setIsFirstEnter(status:Boolean) {
        _isFirstEnter.update { EnterStatusVO(mutableStateOf(status)) }
    }
}