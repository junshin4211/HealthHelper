package com.example.healthhelper.healthyMap.mapVM

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.healthyMap.model.RestaurantInfo
import com.example.healthhelper.signuplogin.UserManager
import com.example.healthhelper.web.httpPost
import com.example.healthhelper.web.serverUrl
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavorListViewModel : ViewModel() {
    private val _favorResturantsState = MutableStateFlow(emptyList<RestaurantInfo>())
    val favorResturantsState: StateFlow<List<RestaurantInfo>> = _favorResturantsState.asStateFlow()

    val userId = UserManager.getUser()?.userId ?: 2

    init {
        viewModelScope.launch {
            _favorResturantsState.value = fetchFavorListByUser()
        }
    }

    fun refreshFavorListData() {
        viewModelScope.launch {
            _favorResturantsState.value = fetchFavorListByUser()
        }
    }

    suspend fun fetchFavorListByUser(): List<RestaurantInfo> {
        val url = "$serverUrl/favorRestaurantList"
        val gson = Gson()
        val jsonObject = JsonObject()
        jsonObject.addProperty("userId", userId)
        return try {
            val result = httpPost(url, jsonObject.toString())
            val rootJsonObject = gson.fromJson(result, JsonObject::class.java)
            val dataJsonArray = rootJsonObject.getAsJsonArray("data")
            val collectionType = object : TypeToken<List<RestaurantInfo>>() {}.type
            gson.fromJson(dataJsonArray, collectionType) ?: emptyList()
        } catch (e: Exception) {
            Log.e("Fetch Error", "Error fetching FavorList from $url: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun insertFavorRestaurant(rid: Int): Boolean {
        val url = "$serverUrl/insertFavorRestaurant"
        val gson = Gson()
        val jsonObject = JsonObject().apply {
            addProperty("userid", userId)
            addProperty("rid", rid)
        }

        val result = httpPost(url, jsonObject.toString())
        val response = gson.fromJson(result, JsonObject::class.java)
        Log.d("dataout", response.get("errMsg").toString())
        if (response.get("result").asBoolean) {
            refreshFavorListData()
        }
        return response.get("result").asBoolean
    }

    suspend fun deleteFavor(rid: Int): Boolean {
        val url = "$serverUrl/deleteFavorRestaurant"
        val gson = Gson()
        val jsonObject = JsonObject().apply {
            addProperty("userId", userId)
            addProperty("rid", rid)
        }

        val result = httpPost(url, jsonObject.toString())
        val response = gson.fromJson(result, JsonObject::class.java)
        Log.d("dataout", response.get("errMsg").toString())

        if (response.get("result").asBoolean) {
            refreshFavorListData()
        }
        return response.get("result").asBoolean
    }
}