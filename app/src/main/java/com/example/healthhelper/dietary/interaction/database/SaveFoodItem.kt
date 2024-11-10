package com.example.healthhelper.dietary.interaction.database

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.healthhelper.R
import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO

// due to use of DisposableEffect, it can not be a composable function,
// and thus, the parameter can not have a default value -- `viewModel()` call.
fun SaveFoodItem(
    context: Context,
    currentUserId:Int,
    currentDiaryId:Int,
    checkedFoodItems: List<SelectedFoodItemVO>,
){
    val TAG = "tag_SaveFoodItem"

    Log.e(TAG,"-".repeat(50))
    Log.e(TAG,"In SaveFoodItem function, currentUserId:${currentUserId}")
    Log.e(TAG,"In SaveFoodItem function, currentDiaryId:${currentDiaryId}")
    Log.e(TAG,"In SaveFoodItem function, checkedFoodItems:${checkedFoodItems}")
    Log.e(TAG,"-".repeat(50))
    Toast.makeText(
        context, context.getString(R.string.save_food_item_successfully),
        Toast.LENGTH_LONG
    ).show()
}
