package com.example.healthhelper.dietary.interaction.database

import android.content.Context
import android.util.Log
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
    val foodItemVOs by foodItemViewModel.data.collectAsState()

    LaunchedEffect(Unit) {
        Log.e(TAG,"-".repeat(50))
        Log.e(TAG,"In LoadFoodItemInfo function, selectedFoodItemVO:${selectedFoodItemVO}")
        val queriedFoodItemVOs =  foodItemViewModel.selectFoodItemByDiaryIdAndMealCategoryId(selectedFoodItemVO)
        Log.e(TAG,"In LoadFoodItemInfo function, queriedFoodItemVOs:${queriedFoodItemVOs}")
        if(queriedFoodItemVOs.isEmpty()){
            return@LaunchedEffect
        }
        Log.e(TAG,"The statement is ready to execute FoodItemRepository.setDatas(queriedFoodItemVOs).")
        FoodItemRepository.setDatas(queriedFoodItemVOs)
        Log.e(TAG,"LaunchedEffect(Unit) block was called.foodItemVOs:${foodItemVOs}")
        Log.e(TAG,"-".repeat(50))
    }
    if(foodItemVOs.isNotEmpty()){
        Log.e(TAG,"Out of LaunchedEffect(Unit) block was called.foodItemVOs:${foodItemVOs}")
    }
}