package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.TimeRange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.sql.Time

object MealsTimeRangeCategoryRepository {
    private val _dataFlow = MutableStateFlow<MutableList<TimeRange>>(mutableListOf<TimeRange>())
    val dataFlow: StateFlow<MutableList<TimeRange>> = _dataFlow.asStateFlow()

    fun fetchMealsTimeRangeCategory(): List<TimeRange>{
        val breakfastStartTime = Time(4,0,0)
        val lunchStartTime = Time(10,0,0)
        val dinnerStartTime = Time(16,0,0)
        val supperStartTime = Time(23,0,0)

        val breakfastTimeRange= TimeRange(breakfastStartTime,lunchStartTime)
        val lunchTimeRange= TimeRange(lunchStartTime,dinnerStartTime)
        val dinnerTimeRange= TimeRange(dinnerStartTime,supperStartTime)
        val supperTimeRange= TimeRange(supperStartTime,breakfastStartTime)

        return listOf(
            breakfastTimeRange,
            lunchTimeRange,
            dinnerTimeRange,
            supperTimeRange,
        )
    }
}