package com.example.healthhelper.dietary.util.localtime

import com.example.healthhelper.dietary.enumclass.TimeComparisonStatusEnum
import java.time.LocalTime

object LocalTimeHandler {
    fun isBetween(
        targetTime: LocalTime,
        startTime: LocalTime,
        endTime: LocalTime,
    ):Int{
        if(startTime >= endTime){
            return TimeComparisonStatusEnum.UNKNOWN_ERROR.statusId
        }
        if(targetTime <= startTime){
            return TimeComparisonStatusEnum.LT.statusId
        }
        if(targetTime >= endTime){
            return TimeComparisonStatusEnum.GT.statusId
        }
        // (expected) executed iff startTime <= targetTime && targetTime <= endTime
        return TimeComparisonStatusEnum.BETWEEN.statusId
    }
}