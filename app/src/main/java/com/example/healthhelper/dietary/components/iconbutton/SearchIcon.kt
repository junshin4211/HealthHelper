package com.example.healthhelper.dietary.components.iconbutton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.dietary.enumclass.DietDiaryScreenEnum

@Composable
fun SearchIcon(
    navController: NavHostController,
) {
    IconButton(onClick = {
        navController.navigate(DietDiaryScreenEnum.DietDiaryMealFrame.name)
    }) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(R.string.search_icon),
        )
    }
}