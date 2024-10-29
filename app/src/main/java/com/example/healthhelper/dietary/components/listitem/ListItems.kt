package com.example.healthhelper.dietary.components.listitem

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import com.example.healthhelper.dietary.viewmodel.SelectedMealOptionViewModel

@Composable
fun ListItems(
    navController: NavHostController,
    selectedMealOptionViewModel: SelectedMealOptionViewModel = viewModel(),
    modifier: Modifier = Modifier,
    foodItems: List<SelectedFoodItemVO>,
){
    foodItems.forEach{ foodItem ->
        ListItem(
            navController = navController,
            modifier = modifier,
            foodItem = foodItem,
        )
    }
}