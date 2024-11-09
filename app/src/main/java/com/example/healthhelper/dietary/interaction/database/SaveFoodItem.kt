package com.example.healthhelper.dietary.interaction.database

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffectResult
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.R
import com.example.healthhelper.dietary.repository.FoodItemRepository
import com.example.healthhelper.dietary.viewmodel.FoodItemViewModel

// due to use of DisposableEffect, it can not be a composable function,
// and thus, the parameter can not have a default value -- `viewModel()` call.
fun SaveFoodItem(
    context: Context,
    foodItemViewModel: FoodItemViewModel,
): DisposableEffectResult {

    if (queriedFoodItemVOs.isEmpty()) {
        Toast.makeText(
            context,
            context.getString(R.string.save_food_item_failed),
            Toast.LENGTH_LONG
        ).show()
        return
    }
    // set all elem of the array into repo -- FoodItemRepository.
    FoodItemRepository.setDatas(queriedFoodItemVOs)
    Toast.makeText(
        context, context.getString(R.string.save_food_item_successfully),
        Toast.LENGTH_LONG
    ).show()
}
