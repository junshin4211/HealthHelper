package com.example.healthhelper.dietary.dataclasses

import java.time.LocalTime

data class TimeRange(
    val startTime: LocalTime,
    val endTime: LocalTime,
)