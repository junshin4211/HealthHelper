import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.ihealth.R
import com.example.ihealth.model.RestaurantInfo
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
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

data class MapState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val selectedRestaurant: RestaurantInfo? = null,
    val showDetails: Boolean = false,
    val showDirections: Boolean = false,
)

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GoogleMapScreen(
    restaurantId: String?,
    restaurants: List<RestaurantInfo>,
    onNavigateToDetails: (String) -> Unit = {},
    onError: (String) -> Unit = {},
    navController: NavHostController,
) {
    var mapState by remember { mutableStateOf(MapState()) }
    val restaurant = restaurants.find { it.id == restaurantId }

    LaunchedEffect(restaurant) {
        if (restaurantId != null && restaurant == null) {
            mapState = mapState.copy(error = "找不到指定餐廳")
            onError("找不到指定餐廳")
        }
    }
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

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
            val restaurantLatLng = LatLng(restaurant.latitude, restaurant.longitude)
            val markerState = rememberMarkerState(position = restaurantLatLng)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(restaurantLatLng, 15f)
            }

            val locationPermission = rememberPermissionState(
                Manifest.permission.ACCESS_FINE_LOCATION
            )

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapLoaded = { mapState = mapState.copy(isLoading = false) },
                properties = MapProperties(
                    isMyLocationEnabled = locationPermission.status.isGranted,
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
                    title = restaurant.name,
                    snippet = restaurant.address,
                    onClick = {
                        mapState = mapState.copy(selectedRestaurant = restaurant)
                        scope.launch { bottomSheetState.expand() }
                        true
                    }
                )
            }
        }

        if (mapState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        mapState.error?.let { error ->
            AlertDialog(
                onDismissRequest = {
                    mapState = mapState.copy(error = null)
                },
                title = { Text("錯誤") },
                text = { Text(error) },
                confirmButton = {
                    TextButton(onClick = {
                        mapState = mapState.copy(error = null)
                    }) {
                        Text("確定")
                    }
                }
            )
        }

        BottomSheetScaffold(
            sheetContent = {
                mapState.selectedRestaurant?.let { restaurant ->
                    RestaurantDetailsBottomSheet(
                        restaurant = restaurant,
                        onDismiss = {
                            scope.launch { bottomSheetState.hide() }
                        },
                        onNavigateToDetails = onNavigateToDetails
                    )
                }
            },
            scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState),
            sheetPeekHeight = 0.dp
        ) {
        }
    }


}

@Composable
private fun RestaurantDetailsBottomSheet(
    restaurant: RestaurantInfo,
    onDismiss: () -> Unit,
    onNavigateToDetails: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = restaurant.name,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = restaurant.address,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))


    }
}

@Composable
fun FloatBackButton(modifier: Modifier = Modifier, navController: NavHostController) {
    Box(
        modifier = modifier
            .background(color = colorResource(R.color.primarycolor), shape = RoundedCornerShape(25.dp))
            .padding(5.dp)
            .clickable(onClick = { navController.navigateUp() })
    ) {
        Icon(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.Center),
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            tint = colorResource(R.color.white),
            contentDescription = stringResource(R.string.back_button),
        )
    }
}
