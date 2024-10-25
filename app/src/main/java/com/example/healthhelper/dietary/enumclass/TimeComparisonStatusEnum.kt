package com.example.healthhelper.dietary.enumclass

enum class TimeComparisonStatusEnum(
    val statusId: Int,
) {
    BETWEEN(statusId = 0),
    LT(statusId = -1),
    GT(statusId = 1),
    UNKNOWN_ERROR(statusId = -2),
}