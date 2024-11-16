package com.example.healthhelper.dietary.interaction.database

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.dietary.repository.FoodItemRepository
import com.example.healthhelper.dietary.viewmodel.FoodItemViewModel

@Composable
fun LoadFoodItemInfo(
    context:Context,
    foodItemViewModel: FoodItemViewModel = viewModel(),
){
    val TAG = "tag_LoadFoodItemInfo"

    val selectedFoodItemVO by foodItemViewModel.selectedData.collectAsState()

    LaunchedEffect(Unit) {
        val queriedFoodItemVOs =  foodItemViewModel.selectFoodItemByDiaryIdAndMealCategoryId(selectedFoodItemVO)
        if(queriedFoodItemVOs.isEmpty()){ return@LaunchedEffect }
        FoodItemRepository.setDatas(queriedFoodItemVOs)
    }
}