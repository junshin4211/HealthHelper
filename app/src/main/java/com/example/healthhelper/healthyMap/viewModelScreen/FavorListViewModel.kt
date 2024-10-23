package com.example.healthhelper.healthyMap.viewModelScreen

import androidx.lifecycle.ViewModel
import com.example.healthhelper.healthyMap.model.ResturantsFavorList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FavorListViewModel: ViewModel() {
    private val _resturantsState = MutableStateFlow(emptyList<ResturantsFavorList>())
    val resturantsState: StateFlow<List<ResturantsFavorList>> = _resturantsState.asStateFlow()

    init {
        _resturantsState.update { fetchFavoriteList() }
    }

    fun addLike(item: ResturantsFavorList) {
        _resturantsState.update {
            it.map { restaurant ->
                if (restaurant.id == item.id) {
                    restaurant.copy(like = 1)
                } else {
                    restaurant
                }
            }
        }
    }

    fun removeLike(item: ResturantsFavorList) {
        _resturantsState.update {
            it.map { restaurant ->
                if (restaurant.id == item.id) {
                    restaurant.copy(like = 0)
                } else {
                    restaurant
                }
            }
        }
    }

    private fun fetchFavoriteList(): List<ResturantsFavorList>{
        return listOf(
            ResturantsFavorList("0001", "麥味登", "新竹縣寶山鄉雙豐路170號", "新竹縣", "寶山鄉", 1 ),
            ResturantsFavorList("0002", "樸實柴窯", "新⽵縣寶⼭鄉寶⼭路⼆段33號", "新竹縣", "寶山鄉", 1 ),
            ResturantsFavorList("0003", "能量先生", "新竹縣竹北市三民路264號", "新竹縣", "竹北市", 1 ),
            ResturantsFavorList("0004", "一勺百茶堂", "新竹縣橫山鄉內灣村中正路271號", "新竹縣", "橫山鄉", 1 ),
            ResturantsFavorList("0005", "煉瓦良茶", "新竹縣竹北市勝利一路156號", "新竹縣", "竹北市", 1 ),
            ResturantsFavorList("0006", "嘉鄉豆腐店", "新竹縣竹北市光明三路62號", "新竹縣", "竹北市", 1 ),
            ResturantsFavorList("0007", "歐加比咖啡", "新竹縣橫山鄉南昌村2鄰薯園1-22號", "新竹縣", "橫山鄉", 1 ),
            ResturantsFavorList("0008", "Z Cafe", "新竹縣尖石鄉錦屏村8鄰42-1號", "新竹縣", "尖石鄉", 1 ),
            ResturantsFavorList("0009", "野山田工坊", "新竹縣峨眉鄉峨眉村2鄰1-2號", "新竹縣", "峨眉鄉", 1 ),
            ResturantsFavorList("0010", "一等香", "新竹縣北埔鄉中山路44號", "新竹縣", "北埔鄉", 1 ),
        )
    }
}