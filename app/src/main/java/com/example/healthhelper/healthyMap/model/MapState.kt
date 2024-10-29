package com.example.healthhelper.healthyMap.model

data class MapState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val selectedRestaurant: RestaurantInfo? = null,
    val showDetails: Boolean = false,
    val showDirections: Boolean = false,
)