package com.example.healthhelper.dietary.interaction.database

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.R
import com.example.healthhelper.dietary.interaction.dataclass.UpdateSelectedFoodItemVOs
import com.example.healthhelper.dietary.repository.FoodItemRepository
import com.example.healthhelper.dietary.viewmodel.FoodItemViewModel
import com.example.healthhelper.dietary.viewmodel.FoodViewModel

@Composable
fun LoadFoodItemInfo(
    context:Context,
    foodItemViewModel: FoodItemViewModel = viewModel(),
    foodViewModel: FoodViewModel = viewModel(),
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
            Toast.makeText(context,context.getString(R.string.load_food_item_info_failed), Toast.LENGTH_LONG).show()
            return@LaunchedEffect
        }
        // set all elem of the array into repo -- FoodItemRepository.
        FoodItemRepository.setDatas(queriedFoodItemVOs)
        Toast.makeText(context,context.getString(R.string.load_food_item_info_successfully),
            Toast.LENGTH_LONG).show()

        Log.e(TAG,"LaunchedEffect(Unit) block was called.foodItemVOs:${foodItemVOs}")
        UpdateSelectedFoodItemVOs(
            context = context,
            foodItemVOs = foodItemVOs,
            foodViewModel = foodViewModel,
        )
        Log.e(TAG,"-".repeat(50))
    }
}