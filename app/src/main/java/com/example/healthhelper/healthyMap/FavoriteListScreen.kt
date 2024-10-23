package com.example.healthhelper.healthyMap

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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.R
import com.example.healthhelper.healthyMap.model.ResturantsFavorList
import com.example.healthhelper.healthyMap.viewModelScreen.FavorListViewModel
import kotlinx.coroutines.launch


@Composable
fun FavoriteListScreen(
    favorListViewModel: FavorListViewModel = viewModel(),
    isFavorite: Boolean
){
    val resturants by favorListViewModel.resturantsState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val filteredResturants = resturants.filter { it.like == 1 }
    Column {
        if (isFavorite) {
            FavoriteList(
//                resturants = resturants,
                resturants = filteredResturants,
                onItemClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "${it.resturantsName}\n${it.address}", withDismissAction = true
                        )
                    }
                },
                onLikeClick = { resturant ->
                    if(resturant.like == 1) {
                        favorListViewModel.removeLike(resturant)
                    }else {
                        favorListViewModel.addLike(resturant)
                    }
                }
            )
        }
    }
}

@Composable
fun FavoriteList(
    resturants: List<ResturantsFavorList>,
    onItemClick: (ResturantsFavorList) -> Unit,
    onLikeClick: (ResturantsFavorList) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(top = 8.dp)
    ) {
        items(resturants) { resturant ->
            ListItem(
                modifier = Modifier.clickable {
                    onItemClick(resturant)
                },
                headlineContent = { Text(resturant.resturantsName) },
                supportingContent = { Text(resturant.address) },
                trailingContent = {
                    IconButton(onClick = {
                        onLikeClick(resturant)
                    }) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = stringResource(R.string.cancelFavor),
                            tint = if (resturant.like == 1) colorResource(R.color.primarycolor) else colorResource(R.color.footer)
                        )
                    }
                }
            )
            HorizontalDivider()
        }
    }
}

