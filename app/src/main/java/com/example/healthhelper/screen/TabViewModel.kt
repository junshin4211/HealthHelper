package com.example.healthhelper.screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TabViewModel:ViewModel() {
    private val _tabVisibility = MutableStateFlow(true)
    val tabVisibility = _tabVisibility.asStateFlow()
    fun setTabVisibility(visible:Boolean) {
        _tabVisibility.value = visible
    }
}