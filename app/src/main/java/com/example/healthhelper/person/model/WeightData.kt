package com.example.healthhelper.person.model

data class WeightData(
    val recordId: Int? = null,
    val height: Double,
    val weight: Double,
    val bodyFat: Double,
    val recordDate: String,
    val bmi: Double,
)
