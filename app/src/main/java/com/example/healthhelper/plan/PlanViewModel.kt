package com.example.healthhelper.plan

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.Date

class PlanViewModel: ViewModel() {
    private val _planState = MutableStateFlow(emptyList<Plan>())
    val planState :StateFlow<List<Plan>> = _planState.asStateFlow()

    init {
        _planState.update { fetchPlanData() }
    }


    private fun fetchPlanData():List<Plan> {
        return listOf(
            Plan(0,0,"高蛋白飲食","2024-10-18 10:00:00","2024-10-18 11:00:00",100f,100f,100f,100f),
            Plan(0,0,"低碳水飲食","2024-10-18 10:00:00","2024-10-18 11:00:00",100f,100f,100f,100f),
            Plan(0,0,"生酮飲食","2024-10-18 10:00:00","2024-10-18 11:00:00",100f,100f,100f,100f),
            Plan(0,0,"地中海飲食","2024-10-18 10:00:00","2024-10-18 11:00:00",100f,100f,100f,100f),
            Plan(0,0,"自訂","2024-10-18 10:00:00","2024-10-18 11:00:00",100f,100f,100f,100f)
        )
    }
}