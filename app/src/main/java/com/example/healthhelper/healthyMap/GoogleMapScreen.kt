package com.example.healthhelper.healthyMap

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.healthhelper.R
import com.example.healthhelper.healthyMap.mapVM.FavorListViewModel
import com.example.healthhelper.healthyMap.model.MapState
import com.example.healthhelper.healthyMap.model.RestaurantInfo
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoogleMapScreen(
    favorListViewModel: FavorListViewModel,
    restaurantId: String?,
    restaurants: List<RestaurantInfo>,
    onError: (String) -> Unit = {},
    navController: NavHostController,
    userLat: Double,
    userLng: Double,
) {
    val favorResturants by favorListViewModel.favorResturantsState.collectAsState()
    val context = LocalContext.current
    var mapState by remember { mutableStateOf(MapState()) }
    val restaurant = restaurants.find { it.rID == restaurantId?.toInt() }
    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(true) }

    LaunchedEffect(restaurant) {
        if (restaurantId != null && restaurant == null) {
            mapState = mapState.copy(error = "找不到指定餐廳")
            onError("找不到指定餐廳")
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FloatBackButton(
            modifier = Modifier
                .zIndex(1f)
                .align(Alignment.TopStart)
                .padding(32.dp)
                .size(50.dp),
            navController = navController
        )
        if (restaurant != null) {
            val restaurantLatLng = LatLng(restaurant.rlatitude, restaurant.rlongitude)
            val markerState = rememberMarkerState(position = restaurantLatLng)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(restaurantLatLng, 15f)
            }
            DirectButton(
                context = context,
                modifier = Modifier
                    .zIndex(1f)
                    .align(Alignment.TopEnd)
                    .padding(32.dp)
                    .size(50.dp),
                userLat = userLat,
                userLng = userLng,
                restaurant = restaurant
            )
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapLoaded = { mapState = mapState.copy(isLoading = false) },
                properties = MapProperties(
                    latLngBoundsForCameraTarget = LatLngBounds(
                        LatLng(22.045858, 119.426224),
                        LatLng(25.161124, 122.343094)
                    ),
                    mapType = MapType.NORMAL,
                    maxZoomPreference = 20f,
                    minZoomPreference = 5f
                ),
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = true,
                    zoomGesturesEnabled = true,
                    compassEnabled = true,
                    mapToolbarEnabled = true
                )
            ) {
                Marker(
                    state = markerState,
                    title = restaurant.rname,
                    snippet = restaurant.raddress,
                    onClick = {
                        mapState = mapState.copy(selectedRestaurant = restaurant)
                        showBottomSheet = true
                        true
                    }
                )
            }
        }
        if (mapState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp)
            )
        }
        mapState.error?.let { error ->
            AlertDialog(
                onDismissRequest = {
                    mapState = mapState.copy(error = null)
                },
                title = { Text(stringResource(R.string.error)) },
                text = { Text(error) },
                confirmButton = {
                    TextButton(onClick = {
                        mapState = mapState.copy(error = null)
                    }) {
                        Text(stringResource(R.string.confirm))
                    }
                }
            )
        }
        if (showBottomSheet) {
            mapState.selectedRestaurant?.let { restaurant ->
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = bottomSheetState,
                    containerColor = colorResource(id = R.color.backgroundcolor)
                ) {
                    RestaurantDetailsBottomSheet(
                        restaurant = restaurant,
                        distance = calculateDistance(
                            userLat,
                            userLng,
                            restaurant.rlatitude,
                            restaurant.rlongitude
                        ),
                        viewModel = favorListViewModel,
                        favorResturants = favorResturants
                    )
                }
            }
        }
    }
}


@Composable
fun RestaurantDetailsBottomSheet(
    restaurant: RestaurantInfo,
    distance: String,
    viewModel: FavorListViewModel,
    favorResturants: List<RestaurantInfo>
) {
    var isFavor by remember { mutableStateOf(favorResturants.any { it.rID == restaurant.rID })}
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = restaurant.rname, fontSize = 28.sp, lineHeight = 30.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = colorResource(R.color.primarycolor),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(5.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(restaurant.rrating.toString())
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = stringResource(R.string.rating),
                                tint = colorResource(R.color.footer)
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = stringResource(R.string.distance),
                            tint = colorResource(R.color.primarycolor)
                        )
                        Text(restaurant.rphone, fontSize = 14.sp)
                    }
                }
                IconButton(onClick = {
                    isFavor = !isFavor
                    scope.launch{
                        if (isFavor) {
                            viewModel.insertFavorRestaurant(restaurant.rID)
                        } else {
                            viewModel.deleteFavor(restaurant.rID)
                        }
                    }
                }) {
                    Icon(
                        imageVector = if (isFavor) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = stringResource(R.string.addFavor),
                        modifier = Modifier.size(50.dp),
                        tint = colorResource(R.color.primarycolor)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                Text(
                    modifier = Modifier.width(250.dp),
                    text = restaurant.raddress,
                    fontSize = 16.sp,
                    lineHeight = 26.sp
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = stringResource(R.string.distance),
                        tint = colorResource(R.color.footer)
                    )
                    Text(distance, color = colorResource(R.color.footer), fontSize = 16.sp)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(R.string.web) + (restaurant.rweb?.ifEmpty { stringResource(R.string.noData) }
                ?: stringResource(R.string.noData)))
            Spacer(modifier = Modifier.height(18.dp))
            Image(
                painter = rememberAsyncImagePainter(
                    restaurant.rphotoUrl?.ifEmpty { "https://images.pexels.com/photos/2741448/pexels-photo-2741448.jpeg?auto=compress&cs=tinysrgb&w=640&h=427&dpr=1" }
                ),
                contentDescription = stringResource(R.string.restaurant_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.FillWidth
            )

        }
    }
}


@Composable
fun FloatBackButton(modifier: Modifier = Modifier, navController: NavHostController) {
    Box(
        modifier = modifier
            .background(
                color = colorResource(R.color.primarycolor),
                shape = RoundedCornerShape(25.dp)
            )
            .padding(5.dp)
            .clickable(onClick = { navController.navigateUp() })
    ) {
        Icon(
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.Center),
            painter = painterResource(R.drawable.arrow_back_ios_new),
            tint = colorResource(R.color.white),
            contentDescription = stringResource(R.string.back_button),
        )
    }
}

@Composable
fun DirectButton(
    modifier: Modifier = Modifier,
    context: Context,
    userLat: Double,
    userLng: Double,
    restaurant: RestaurantInfo,
) {
    Box(
        modifier = modifier
            .background(
                color = colorResource(R.color.primarycolor),
                shape = RoundedCornerShape(25.dp)
            )
            .padding(5.dp)
            .clickable(onClick = {
                openMapApp(
                    context,
                    userLat,
                    userLng,
                    restaurant.rlatitude,
                    restaurant.rlongitude,
                )
            })
    ) {
        Icon(
            modifier = Modifier
                .size(40.dp)
                .rotate(45f)
                .align(Alignment.Center),
            painter = painterResource(R.drawable.baseline_navigation_24),
            tint = colorResource(R.color.white),
            contentDescription = stringResource(R.string.go2MapAPP),
        )
    }
}

private fun openMapApp(
    context: Context,
    userLat: Double,
    userLng: Double,
    restaurantLat: Double,
    restaurantLng: Double,
) {
    val uriStr = "https://www.google.com/maps/dir/?api=1" +
            "&origin=$userLat,$userLng&destination=$restaurantLat,$restaurantLng" +
            "&travelmode=driving"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uriStr))
    intent.setClassName(
        "com.google.android.apps.maps",
        "com.google.android.maps.MapsActivity"
    )
    context.startActivity(intent)
}
