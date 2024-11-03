package com.example.healthhelper.healthyMap

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.healthyMap.mapVM.FavorListViewModel
import com.example.healthhelper.healthyMap.model.RestaurantInfo
import kotlinx.coroutines.launch


@Composable
fun FavoriteListScreen(
    favorListViewModel: FavorListViewModel,
    isFavorite: Boolean,
    navController: NavHostController,
) {
    val favorResturants by favorListViewModel.favorResturantsState.collectAsState()
    val scope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        favorListViewModel.fetchFavorListByUser()
    }
    Column {
        if (isFavorite) {
            FavoriteList(
                resturants = favorResturants,
                onItemClick = { restaurant ->
                    navController.navigate("${MapScreenEnum.GoogleMapScreen.name}/${restaurant.rID}")
                },
                onLikeClick = { resturant ->
                    scope.launch {
                        favorListViewModel.deleteFavor(resturant.rID)
                    }
                }
            )
        }
    }
}

@Composable
fun FavoriteList(
    resturants: List<RestaurantInfo>,
    onItemClick: (RestaurantInfo) -> Unit,
    onLikeClick: (RestaurantInfo) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(top = 8.dp)
    ) {
        items(resturants) { resturant ->
            ListItem(
                modifier = Modifier.clickable {
                    onItemClick(resturant)
                },
                colors = ListItemDefaults.colors(colorResource(R.color.backgroundcolor)),
                headlineContent = { Text(resturant.rname) },
                supportingContent = { Text(resturant.raddress) },
                trailingContent = {
                    IconButton(onClick = {
                        onLikeClick(resturant)
                    }) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = stringResource(R.string.cancelFavor),
                            tint = colorResource(R.color.primarycolor)
                        )
                    }
                }
            )
            HorizontalDivider()
        }
    }
}

