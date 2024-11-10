package com.example.healthhelper.dietary.interaction.database

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.R
import com.example.healthhelper.dietary.dataclasses.vo.FoodItemVO
import com.example.healthhelper.dietary.repository.FoodItemRepository
import com.example.healthhelper.dietary.viewmodel.FoodItemViewModel
import kotlinx.coroutines.launch

fun LoadFoodItem(
    context: Context,
    selectedFoodItemVO: FoodItemVO,
    foodItemViewModel: FoodItemViewModel,
) {
    val TAG = "tag_LoadFoodItem"

    foodItemViewModel.viewModelScope.launch {
        Log.e(TAG,"-".repeat(50))
        Log.e(TAG,"In LoadFoodItem function,selectedFoodItemVO:${selectedFoodItemVO}")
        val queriedFoodItemVOs = foodItemViewModel.selectFoodItemByDiaryId(selectedFoodItemVO)
        Log.e(TAG,"In LoadFoodItem function,queriedFoodItemVOs:${queriedFoodItemVOs}")
        Log.e(TAG,"In LoadFoodItem function,selectedFoodItemVO:${selectedFoodItemVO}")
        Log.e(TAG,"-".repeat(50))
        if (queriedFoodItemVOs.isEmpty()) {
            Toast.makeText(
                context,
                context.getString(R.string.load_food_item_failed),
                Toast.LENGTH_LONG
            ).show()
            return@launch
        }
        // set all elem of the array into repo -- FoodItemRepository.
        FoodItemRepository.setDatas(queriedFoodItemVOs)
        Toast.makeText(
            context, context.getString(R.string.load_food_item_successfully),
            Toast.LENGTH_LONG
        ).show()
    }
}