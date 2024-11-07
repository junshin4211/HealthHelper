package com.example.healthhelper.healthyMap.mapVM

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.healthyMap.model.RestaurantInfo
import com.example.healthhelper.web.httpPost
import com.example.healthhelper.web.serverUrl
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RestaurantViewModel : ViewModel() {
    private val _restaurantsByDistrict = MutableStateFlow<Map<String, List<RestaurantInfo>>>(emptyMap())
    val restaurantsByDistrict: StateFlow<Map<String, List<RestaurantInfo>>> = _restaurantsByDistrict.asStateFlow()

    private val _allRestaurants = MutableStateFlow<List<RestaurantInfo>>(emptyList())
    val allRestaurants: StateFlow<List<RestaurantInfo>> = _allRestaurants.asStateFlow()

    fun fetchAndStoreRestaurantsByCity(city: String) {
        viewModelScope.launch {
            val fetchedRestaurants = fetchRestaurantListByCity(city)
            _allRestaurants.value = fetchedRestaurants
            _restaurantsByDistrict.value = fetchedRestaurants.groupBy { it.rregion }
        }
    }

    suspend fun fetchRestaurantListByCity(city: String): List<RestaurantInfo> {
        val url = "$serverUrl/selectRestaurantByCity"
        val gson = Gson()
        val jsonObject = JsonObject().apply {
            addProperty("rcity", city)
        }

        return try {
            val result = httpPost(url, jsonObject.toString())
            val rootJsonObject = gson.fromJson(result, JsonObject::class.java)
            val dataJsonArray = rootJsonObject.getAsJsonArray("data")
            val collectionType = object : TypeToken<List<RestaurantInfo>>() {}.type
            gson.fromJson(dataJsonArray, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching Restaurant from $url: ${e.message}", e)
            emptyList()
        }
    }
}
