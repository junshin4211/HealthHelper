package com.example.healthhelper.healthyMap.model

data class RestaurantInfo(
    val id: String,
    val name: String,
    val city: String,
    val region: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
)