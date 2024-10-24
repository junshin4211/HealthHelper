package com.example.healthhelper.dietary.repository

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.healthhelper.dietary.dataclasses.TimeRange
import com.example.healthhelper.dietary.enumclass.TimeComparisonStatusEnum
import com.example.healthhelper.dietary.enumclass.TimeFoundStatusEnum
import com.example.healthhelper.dietary.util.localtime.LocalTimeHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import java.time.LocalTime

object MealsTimeRangeCategoryRepository {
    val TAG = "tag_MealsTimeRangeCategoryRepository"

    private val _dataFlow = MutableStateFlow<MutableList<TimeRange>>(mutableListOf<TimeRange>())
    val dataFlow: StateFlow<MutableList<TimeRange>> = _dataFlow.asStateFlow()

    @Composable
    // return index
    fun categorizeMeal(mealTime:LocalTime):Int{
        val timeRanges = fetchMealsTimeRangeCategory()

        val breakfastStartTime = timeRanges[0].startTime
        val supperEndTime = timeRanges.last().endTime

        if(mealTime <= breakfastStartTime || mealTime >= supperEndTime){
            return timeRanges.size - 1
        }

        for(index in 0..<timeRanges.size-1){
            val timeRange = timeRanges[index]
            val startTime = timeRange.startTime
            val endTime = timeRange.endTime

            val isBetweenRetValue = LocalTimeHandler.isBetween(
                mealTime,
                startTime,
                endTime,
            )

            if(isBetweenRetValue == TimeComparisonStatusEnum.BETWEEN.statusId ){
                return index
            }

            if(isBetweenRetValue == TimeComparisonStatusEnum.UNKNOWN_ERROR.statusId){
                return TimeFoundStatusEnum.UNKNOWN_ERROR.statusId
            }
        }

        return TimeFoundStatusEnum.NOT_FOUND.statusId
    }

    fun fetchMealsTimeRangeCategory(): List<TimeRange>{
        val breakfastStartTime = LocalTime.of(4,0)
        val lunchStartTime = LocalTime.of(10,0)
        val dinnerStartTime = LocalTime.of(16,0)
        val supperStartTime = LocalTime.of(22,0)

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