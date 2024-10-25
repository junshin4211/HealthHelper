package com.example.healthhelper.dietary.components.bar.appbar.topappbar

import android.util.Log
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.healthhelper.dietary.components.iconbutton.SearchIcon
import com.example.healthhelper.dietary.components.iconbutton.navigationicon.NavigateUpNavigationIcon
import com.example.healthhelper.dietary.viewmodel.MealsOptionViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun QueryTopAppBar(
    navController: NavHostController,
    title: @Composable () -> Unit,
    mealsOptionViewModel: MealsOptionViewModel = viewModel(),
    selectedMealsOptionIndex: MutableState<Int>,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    ),
) {
    val TAG = "tag_QueryTopAppBar"

    val mealsOptions by  mealsOptionViewModel.data.collectAsState()

    Log.d(TAG,"mealsOptions:$mealsOptions")

    val selectedMealsOption = mealsOptions[selectedMealsOptionIndex.value]

    Log.d(TAG,"selectedMealsOptionIndex.value:${selectedMealsOptionIndex.value}")
    Log.d(TAG,"selectedMealsOption:${selectedMealsOption}")

    CenterAlignedTopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = {
            NavigateUpNavigationIcon(navController)
        },
        actions = {
            SearchIcon(navController, selectedMealsOption)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        scrollBehavior = scrollBehavior,
    )
}