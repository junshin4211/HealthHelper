package com.example.healthhelper.healthyMap.model

data class RestaurantInfo(
    val rID: Int,
    val rname: String,
    val rcity: String,
    val rregion: String,
    val raddress: String,
    val rlatitude: Double,
    val rlongitude: Double,
    val rrating: Float,
    val rphone: String,
    val rweb: String? = null,
    val rphotoUrl: String? = null,
    val ufid: Int? = null
)