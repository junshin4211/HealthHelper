package com.example.healthhelper.dietary.enumclass

enum class TimeFoundStatusEnum(
    val statusId: Int,
) {
    UNKNOWN_ERROR(statusId = -2),
    NOT_FOUND(statusId = -1),
}