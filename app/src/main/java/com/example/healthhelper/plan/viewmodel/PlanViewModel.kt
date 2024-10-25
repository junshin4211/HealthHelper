package com.example.healthhelper.plan.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthhelper.plan.model.PlanProperty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlanViewModel: ViewModel() {
    private val _planState = MutableStateFlow(emptyList<PlanProperty>())
    val planState :StateFlow<List<PlanProperty>> = _planState.asStateFlow()

    init {
        _planState.update { fetchPlanData() }
    }


    private fun fetchPlanData():List<PlanProperty> {
        return listOf(
            PlanProperty(0,0,"高蛋白飲食","2024-10-18 10:00:00","2024-10-18 11:00:00",100f,100f,100f,100f),
            PlanProperty(0,0,"低碳水飲食","2024-10-18 10:00:00","2024-10-18 11:00:00",100f,100f,100f,100f),
            PlanProperty(0,0,"生酮飲食","2024-10-18 10:00:00","2024-10-18 11:00:00",100f,100f,100f,100f),
            PlanProperty(0,0,"地中海飲食","2024-10-18 10:00:00","2024-10-18 11:00:00",100f,100f,100f,100f),
            PlanProperty(0,0,"自訂","2024-10-18 10:00:00","2024-10-18 11:00:00",100f,100f,100f,100f)
        )
    }
}