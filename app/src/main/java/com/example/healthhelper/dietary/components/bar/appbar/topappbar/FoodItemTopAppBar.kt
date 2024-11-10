package com.example.healthhelper.dietary.components.bar.appbar.topappbar

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.healthhelper.attr.viewmodel.DefaultColorViewModel
import com.example.healthhelper.dietary.components.iconbutton.SearchIcon
import com.example.healthhelper.dietary.components.iconbutton.navigationicon.NavigateUpNavigationIcon
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItemTopAppBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    ),
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = DefaultColorViewModel.topAppBarColors,
        title = title,
        navigationIcon = {
            NavigateUpNavigationIcon(navController)
        },
        actions = {
            SearchIcon(
                navController = navController,
                onClick = {navController.navigate(DietDiaryScreenEnum.DietDiaryMealFrame.name)},
            )
        },
        scrollBehavior = scrollBehavior,
    )
}