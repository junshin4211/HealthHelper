package com.example.healthhelper.healthyMap

import android.Manifest
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.healthyMap.mapVM.FavorListViewModel
import com.example.healthhelper.healthyMap.mapVM.RestaurantViewModel
import com.example.healthhelper.healthyMap.model.RestaurantInfo
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainMapSearchScreen(
    restaurantViewModel: RestaurantViewModel = viewModel(),
    favorListViewModel: FavorListViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val destination = navController.currentBackStackEntryAsState().value?.destination
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val viewModel: RestaurantViewModel = viewModel()
    val restaurants by viewModel.restaurantsByDistrict.collectAsState()


    var userLat by remember { mutableDoubleStateOf(0.0) }
    var userLng by remember { mutableDoubleStateOf(0.0) }
    var userCity by remember { mutableStateOf("") }
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val permissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    LaunchedEffect(userLat) {
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        } else {
            getLocationUpdates(fusedLocationClient) { latitude, longitude ->
                userLat = latitude
                userLng = longitude
            }
        }
    }

    val restaurantsByDistrict by restaurantViewModel.restaurantsByDistrict.collectAsState()
    val allRestaurants by restaurantViewModel.allRestaurants.collectAsState()
    LaunchedEffect(userCity) {
        restaurantViewModel.fetchAndStoreRestaurantsByCity(userCity)
    }


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (!isShowTopBar(destination)) {
                userCity = ReverseGeocode(context, userLat, userLng)
                MapSearchAppBar(
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() },
                    navController = navController,
                    userCity = if (userCity == "") stringResource(R.string.LocationNone) else userCity
                )
            }
        },
        containerColor = colorResource(R.color.backgroundcolor)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            HorizontalDivider(
                color = colorResource(id = R.color.primarycolor),
                thickness = 2.dp
            )

            NavHost(
                navController = navController,
                startDestination = MapScreenEnum.MapSearchScreen.name,
                modifier = Modifier
                    .fillMaxSize()
                    //.padding(innerPadding)
            ) {
                composable(route = MapScreenEnum.MapSearchScreen.name) {
                    MapSearchScreen(
                        restaurantsByDistrict = restaurantsByDistrict,
                        allRestaurants = allRestaurants,
                        navController = navController,
                        userLat = userLat,
                        userLng = userLng,
                    )
                }
                composable(
                    route = "${MapScreenEnum.GoogleMapScreen.name}/{restaurantId}"
                ) { backStackEntry ->
                    val restaurantId = backStackEntry.arguments?.getString("restaurantId")
                    GoogleMapScreen(
                        favorListViewModel = favorListViewModel,
                        restaurantId = restaurantId,
                        restaurants = restaurants.values.flatten(),
                        navController = navController,
                        userLat = userLat,
                        userLng = userLng
                    )
                }
                composable(
                    route = "${MapScreenEnum.FavoriteListScreen.name}"
                ) {
                    FavoriteListScreen(
                        favorListViewModel = favorListViewModel,
                        isFavorite = true,
                        navController = navController
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapSearchAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
//    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController,
    userCity: String,
) {
    var isFavorite by remember { mutableStateOf(false) }
    val favoriteColor = animateColorAsState(
        targetValue = if (isFavorite) colorResource(R.color.primarycolor) else colorResource(R.color.footer),
        animationSpec = tween(durationMillis = 300)
    )
    TopAppBar(
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.currentSite), fontSize = 16.sp)
                Text(text = userCity, fontSize = 16.sp)
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = colorResource(R.color.backgroundcolor)
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = {
                    navigateUp()
                    isFavorite = false
                }) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back_ios_new),
                        tint = colorResource(R.color.primarycolor),
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {
                isFavorite = !isFavorite
                val currentDestination = navController.currentDestination?.route
                if (currentDestination == MapScreenEnum.FavoriteListScreen.name) {
                    navController.popBackStack()
                } else {
                    navController.navigate(MapScreenEnum.FavoriteListScreen.name)
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_favorite_24),
                    contentDescription = stringResource(id = R.string.favoriteList),
                    tint = favoriteColor.value,
                    modifier = Modifier.size(48.dp)
                )
            }
        },
    )
}


fun isShowTopBar(destination: NavDestination?): Boolean {
    return destination?.route?.startsWith("GoogleMapScreen") == true
}

private fun getLocationUpdates(
    fusedLocationClient: FusedLocationProviderClient,
    onLocationUpdated: (Double, Double) -> Unit,
) {
    val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        600000
    ).setMinUpdateDistanceMeters(1000f)
        .build()

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let { location ->
                onLocationUpdated(location.latitude, location.longitude)
            } ?: run {
                Log.e("LocationHandler", "Location is null.")
            }
        }
    }

    fusedLocationClient.requestLocationUpdates(
        locationRequest, locationCallback, Looper.getMainLooper()
    )

    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) {
            onLocationUpdated(location.latitude, location.longitude)
        } else {
            Log.e("LocationHandler", "Failed to get last known location.")
        }
    }
}

@Composable
private fun ReverseGeocode(context: Context, userLat: Double, userLng: Double): String {
    var userCity = remember { mutableStateOf<String?>(null) }
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        geocoder.getFromLocation(userLat, userLng, 1) { address ->
            val address = address[0]
            val subLocality = address.adminArea
            userCity.value = subLocality
        }
    } catch (e: Exception) {
        Log.e("Geocoder", "Error retrieving address: $e")
        userCity.value = "Error"
    }
    return userCity.value ?: "Unknown"
}


