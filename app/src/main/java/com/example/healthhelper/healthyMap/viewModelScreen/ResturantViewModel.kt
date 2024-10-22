package com.example.healthhelper.healthyMap.viewModelScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.healthyMap.model.RestaurantInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class RestaurantViewModel : ViewModel() {
    private val _restaurantsByDistrict = MutableStateFlow<Map<String, List<RestaurantInfo>>>(emptyMap())
    val restaurantsByDistrict: StateFlow<Map<String, List<RestaurantInfo>>> = _restaurantsByDistrict.asStateFlow()

    private val _allRestaurants = MutableStateFlow<List<RestaurantInfo>>(emptyList())
    val allRestaurants: StateFlow<List<RestaurantInfo>> = _allRestaurants.asStateFlow()

    init {
        fetchRestaurants()
    }
    fun fetchRestaurants() {
        viewModelScope.launch {
            val fetchedRestaurants = getRestaurantsFromApi()
            _allRestaurants.value = fetchedRestaurants
            _restaurantsByDistrict.value = fetchedRestaurants.groupBy { it.region }
        }
    }
    fun getRestaurantsByDistrict(district: String): List<RestaurantInfo> {
        return restaurantsByDistrict.value[district] ?: emptyList()
    }

    fun getAllRestaurants(): List<RestaurantInfo> {
        return allRestaurants.value
    }

    private fun getRestaurantsFromApi(): List<RestaurantInfo> {
        return listOf(
            RestaurantInfo("0001", "誠食健康蔬食南京復興店", "台北市", "中山區", "臺北市中山區南京東路三段303巷7弄1-1號", 25.0522, 121.5538, 4.2, "02 2358 2356","0937 556 771","https://lin.ee/mdU7NVv"),
            RestaurantInfo("0002", "綠原品健康蔬食全自助餐-大安復興店", "台北市", "大安區", "臺北市大安區復興南路一段291號", 25.0423, 121.5486, 4.2, "02 2358 2356","0937 556 771","https://lin.ee/mdU7NVv"),
            RestaurantInfo("0003", "新天母素食館", "台北市", "北投區", "臺北市北投區石牌路二段99巷47號", 25.1318, 121.5025, 4.2, "02 2358 2356","0937 556 771","https://lin.ee/mdU7NVv"),
            RestaurantInfo("0004", "TG 蔬坊 The Greenery 南港環球店", "台北市", "南港區", "臺北市南港區忠孝東路七段371號環球購物中心(南港車站)7樓", 25.0484, 121.6150, 4.2, "02 2358 2356","0937 556 771","https://lin.ee/mdU7NVv"),
            RestaurantInfo("0005", "樂坡BonBox – 臺北南京三民店", "台北市", "松山區", "臺北市松山區南京東路五段123巷2弄1-1號", 25.0481, 121.5736, 4.2, "02 2358 2356","0937 556 771","https://lin.ee/mdU7NVv"),
            RestaurantInfo("0006", "瑪克盃全豆漿", "台北市", "南港區", "臺北市南港區東新街80巷5-1號", 25.0528, 121.6174, 4.2, "02 2358 2356","0937 556 771","https://lin.ee/mdU7NVv"),
            RestaurantInfo("0007", "TAIGA針葉林", "台北市", "內湖區", "臺北市內湖區金龍路187號", 25.0668, 121.5866, 4.2, "02 2358 2356","0937 556 771","https://lin.ee/mdU7NVv"),
            RestaurantInfo("0008", "新東王烘焙坊", "台北市", "中正區", "臺北市中正區南昌路一段61號", 25.0378, 121.5113, 4.2, "02 2358 2356","0937 556 771","https://lin.ee/mdU7NVv"),
            RestaurantInfo("0090", "GET POWER 給力盒子 永春店", "台北市", "信義區", "臺北市信義區虎林街121巷8號1樓", 25.0370, 121.5783, 4.2, "02 2358 2356","0937 556 771","https://lin.ee/mdU7NVv"),
            RestaurantInfo("0010", "多肉廚房", "台北市", "大同區", "臺北市大同區太原路175巷30號1樓", 25.0672, 121.5154, 4.2, "02 2358 2356","0937 556 771","https://lin.ee/mdU7NVv"),
            RestaurantInfo("0011", "Miss Energy 能量小姐-臺北承德店", "台北市", "大同區", "臺北市大同區承德路二段151號", 25.0685, 121.5209, 4.2, "02 2358 2356","0937 556 771","https://lin.ee/mdU7NVv"),
            RestaurantInfo("0012", "Herbar喝吧 忠孝旗艦店", "台北市", "大安區", "臺北市大安區大安路1段83巷7號", 25.0383, 121.5470, 4.2, "02 2358 2356","0937 556 771","https://lin.ee/mdU7NVv"),
            RestaurantInfo("0013", "鍬記打邊爐", "桃園市", "大溪區", "桃園市大溪區慈光街79巷31弄11號", 24.90237, 121.2797, 4.2, "02 2358 2356","0937 556 771","https://lin.ee/mdU7NVv"),
        )
    }
}