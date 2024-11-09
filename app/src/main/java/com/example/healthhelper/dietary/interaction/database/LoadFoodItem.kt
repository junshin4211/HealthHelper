package com.example.healthhelper.dietary.interaction.database

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.R
import com.example.healthhelper.dietary.repository.FoodItemRepository
import com.example.healthhelper.dietary.viewmodel.FoodItemViewModel

@Composable
fun LoadFoodItem(
    context:Context,
    foodItemViewModel: FoodItemViewModel = viewModel(),
){
    val selectedFoodItemVO by foodItemViewModel.selectedData.collectAsState()

    LaunchedEffect(Unit) {
        val queriedFoodItemVOs =  foodItemViewModel.selectFoodItemByDiaryIdAndMealCategoryId(selectedFoodItemVO)

        if(queriedFoodItemVOs.isEmpty()){
            Toast.makeText(context,context.getString(R.string.load_food_item_failed), Toast.LENGTH_LONG).show()
            return@LaunchedEffect
        }
        // set all elem of the array into repo -- FoodItemRepository.
        FoodItemRepository.setDatas(queriedFoodItemVOs)
        Toast.makeText(context,context.getString(R.string.load_food_item_successfully),
            Toast.LENGTH_LONG).show()
    }
}