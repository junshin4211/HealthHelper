package com.example.healthhelper.healthyMap

import android.location.Location
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.healthyMap.model.City
import com.example.healthhelper.healthyMap.model.District
import com.example.healthhelper.healthyMap.model.LatLngRange
import com.example.healthhelper.healthyMap.model.RestaurantInfo
import com.example.healthhelper.healthyMap.source.CityDistrictsLoader


@Composable
fun MapSearchScreen(
    restaurantsByDistrict: Map<String, List<RestaurantInfo>>,
    allRestaurants: List<RestaurantInfo>,
    navController: NavHostController,
    userLat: Double,
    userLng: Double,
) {
    var inputText by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf<City?>(null) }
    var selectedDistrict by remember { mutableStateOf<District?>(null) }
    var selectedSearchMethod by remember { mutableStateOf(0) }
    var isShowSearchMethod by remember { mutableStateOf(true) }

    var textFieldCurrentListMethod by remember { mutableIntStateOf(1) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(
                    4.dp,
                    colorResource(R.color.primarycolor),
                    shape = RoundedCornerShape(15.dp)
                ),
            value = inputText,
            onValueChange = { inputText = it },
            placeholder = { Text(text = stringResource(R.string.searchMap)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = "search",
                    tint = colorResource(R.color.primarycolor)
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.clearSearch),
                    modifier = Modifier.clickable { inputText = "" },
                    tint = colorResource(R.color.primarycolor)
                )
            },
            shape = RoundedCornerShape(15.dp),
            singleLine = true,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally)
        ) {
            if (isShowSearchMethod) {
                Button(
                    onClick = {
                        textFieldCurrentListMethod = 1
                        selectedSearchMethod = 0
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedSearchMethod == 0) colorResource(R.color.primarycolor) else colorResource(
                            R.color.backgroundcolor
                        ),
                    ),
                    border = BorderStroke(1.dp, colorResource(R.color.primarycolor)),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.currentSite),
                        color = if (selectedSearchMethod == 0) colorResource(R.color.white) else colorResource(
                            R.color.primarycolor
                        )
                    )
                }
                Button(
                    onClick = { selectedSearchMethod = 1 },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedSearchMethod == 1) colorResource(R.color.primarycolor) else colorResource(
                            R.color.backgroundcolor
                        )
                    ),
                    border = BorderStroke(1.dp, colorResource(R.color.primarycolor)),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.citySearch),
                        color = if (selectedSearchMethod == 1) colorResource(R.color.white) else colorResource(
                            R.color.primarycolor
                        )
                    )
                }
            }
        }
        if (inputText.isNotEmpty()) {
            textFieldCurrentListMethod = 2
            RestaurantFilter(
                textFieldCurrentListMethod,
                restaurants = allRestaurants,
                inputText = inputText,
                userLat = userLat,
                userLng = userLng,
                navController = navController
            )
        }

        if (selectedSearchMethod == 0) {
            textFieldCurrentListMethod = 1
            RestaurantFilter(
                textFieldCurrentListMethod,
                restaurants = allRestaurants,
                inputText = inputText,
                userLat = userLat,
                userLng = userLng,
                navController = navController
            )
        }

        if (selectedSearchMethod == 1) {
            when {
                selectedDistrict != null -> {
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Button(
                            onClick = { selectedDistrict = null },
                            colors = ButtonDefaults.buttonColors(colorResource(R.color.primarycolor))
                        ) {
                            Text(stringResource(R.string.backRegionList))
                        }
                    }
                    RestaurantList(
                        district = selectedDistrict!!,
                        restaurantsByDistrict = restaurantsByDistrict,
                        userLat = userLat,
                        userLng = userLng,
                        navController = navController
                    )

                    isShowSearchMethod = false

                }

                selectedCity != null -> {
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Button(
                            onClick = { selectedCity = null },
                            colors = ButtonDefaults.buttonColors(colorResource(R.color.primarycolor))
                        ) {
                            Text(stringResource(R.string.backCityList))
                        }
                    }
                    RegionList(
                        city = selectedCity!!,
                        onItemClick = { district ->
                            selectedDistrict = district
                        },
                    )
                    isShowSearchMethod = false
                }

                else -> {
                    CityList(
                        R.raw.taiwan_districts,
                        onItemClick = { city ->
                            selectedCity = city
                        }
                    )
                    isShowSearchMethod = true
                }
            }
        }
    }

}

@Composable
fun CityList(
    fileName: Int,
    onItemClick: (City) -> Unit,
) {
    val context = LocalContext.current
    val cityLoader = remember { CityDistrictsLoader(fileName) }
    val cities = remember { cityLoader.getCityDistrictsData(context) }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(cities) { city ->
            ListItem(
                modifier = Modifier.clickable { onItemClick(city) },
                colors = ListItemDefaults.colors(colorResource(R.color.backgroundcolor)),
                headlineContent = { Text(city.name) },
                trailingContent = {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = stringResource(R.string.go2regionList),
                        tint = colorResource(R.color.primarycolor)
                    )
                }
            )
            HorizontalDivider(color = colorResource(R.color.primarycolor))
        }
    }
}

@Composable
fun RegionList(
    city: City,
    onItemClick: (District) -> Unit,
) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(city.districts) { district ->
            ListItem(
                modifier = Modifier.clickable { onItemClick(district) },
                colors = ListItemDefaults.colors(colorResource(R.color.backgroundcolor)),
                headlineContent = { Text(district.name) },
                trailingContent = {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = stringResource(R.string.go2restaurantList),
                        tint = colorResource(R.color.primarycolor)
                    )
                }
            )
            HorizontalDivider(color = colorResource(R.color.primarycolor))
        }
    }
}

@Composable
fun RestaurantList(
    navController: NavController,
    district: District,
    userLat: Double?,
    userLng: Double?,
    restaurantsByDistrict: Map<String, List<RestaurantInfo>>,
) {
    val restaurants = restaurantsByDistrict[district.name] ?: emptyList()
    val sortedRestaurants = restaurants.map { restaurant ->
        val distance = if (userLat != null && userLng != null) {
            calculateDistance(userLat, userLng, restaurant.rlatitude, restaurant.rlongitude)
        } else {
            "N/A"
        }
        restaurant to distance
    }.sortedBy { it.second }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(sortedRestaurants) { (restaurant, distance) ->
            RestaurantListItem(navController, restaurant, distance)
        }
    }
}

@Composable
fun RestaurantFilter(
    textFieldCurrentListMethod: Int,
    restaurants: List<RestaurantInfo>,
    inputText: String,
    userLat: Double?,
    userLng: Double?,
    navController: NavController,
) {
    val latLngRange = findNearbyRestaurantsLatLngRange(userLat, userLng, distance = 2.0)

    val filteredRestaurants = when (textFieldCurrentListMethod) {
        1 -> {
            if (latLngRange != null) {
                restaurants.filter { restaurant ->
                    restaurant.rlatitude in latLngRange.minLat..latLngRange.maxLat &&
                            restaurant.rlongitude in latLngRange.minLng..latLngRange.maxLng
                }
            } else {
                emptyList()
            }
        }

        2 -> {
            restaurants.filter {
                it.rname.contains(inputText, ignoreCase = true) || it.rregion.contains(
                    inputText,
                    ignoreCase = true
                )
            }
        }

        else -> throw IllegalArgumentException("餐廳列表顯示錯誤")
    }

    // 計算距離並排序
    val sortedFilteredRestaurants = filteredRestaurants.map { restaurant ->
        val distance = if (userLat != null && userLng != null) {
            calculateDistance(userLat, userLng, restaurant.rlatitude, restaurant.rlongitude)
        } else {
            "N/A"
        }
        restaurant to distance
    }.sortedBy { it.second } // 根據距離排序

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(sortedFilteredRestaurants) { (restaurant, distance) ->
            RestaurantListItem(navController, restaurant, distance)
        }
        if (sortedFilteredRestaurants.isEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.noMatchRestaurant),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


@Composable
fun RestaurantListItem(
    navController: NavController,
    restaurant: RestaurantInfo,
    distance: String,
) {
    ListItem(
        modifier = Modifier.clickable {
            navController.navigate("${MapScreenEnum.GoogleMapScreen.name}/${restaurant.rID}")
        },
        colors = ListItemDefaults.colors(colorResource(R.color.backgroundcolor)),
        headlineContent = { Text(restaurant.rname) },
        supportingContent = { Text(restaurant.raddress) },
        trailingContent = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = stringResource(R.string.distance),
                    tint = colorResource(R.color.footer)
                )
                Text(distance, color = colorResource(R.color.footer), fontSize = 14.sp)
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = stringResource(R.string.go2Map),
                    tint = colorResource(R.color.primarycolor)
                )
            }
        }
    )
    HorizontalDivider(color = colorResource(R.color.primarycolor))
}

fun findNearbyRestaurantsLatLngRange(
    userLat: Double?,
    userLng: Double?,
    distance: Double,
): LatLngRange? {

    if (userLat == null || userLng == null) {
        return throw IllegalArgumentException("使用者尚未定位")
    }

    val earthRadius = 6371.0
    val latChange = distance / earthRadius * (180 / Math.PI)
    val lngChange = distance / (earthRadius * Math.cos(Math.toRadians(userLat))) * (180 / Math.PI)

    val minLat = userLat - latChange
    val maxLat = userLat + latChange
    val minLng = userLng - lngChange
    val maxLng = userLng + lngChange

    return LatLngRange(minLat, maxLat, minLng, maxLng)
}

fun calculateDistance(
    userLat: Double,
    userLng: Double,
    restaurantLat: Double,
    restaurantLng: Double,
): String {
    val results = FloatArray(1)
    Location.distanceBetween(
        userLat,
        userLng,
        restaurantLat,
        restaurantLng,
        results
    )
    val distanceInMeters = results[0]
    return if (distanceInMeters < 100) {
        String.format("${distanceInMeters.toInt()} m")
    } else {
        String.format("%.1f km", distanceInMeters / 1000)
    }
}






