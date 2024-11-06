package com.example.healthhelper.dietary.components.bar.appbar.topappbar

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.healthhelper.attr.color.defaultcolor.DefaultColorViewModel
import com.example.healthhelper.dietary.components.iconbutton.SearchIcon
import com.example.healthhelper.dietary.components.iconbutton.navigationicon.NavigateUpNavigationIcon
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum
import com.example.healthhelper.dietary.viewmodel.MealsOptionViewModel
import com.example.healthhelper.dietary.viewmodel.SelectedMealOptionViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun QueryTopAppBar(
    navController: NavHostController,
    mealsOptionViewModel: MealsOptionViewModel = viewModel(),
    selectedMealOptionViewModel: SelectedMealOptionViewModel = viewModel(),
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    ),
) {
    val TAG = "tag_QueryTopAppBar"

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