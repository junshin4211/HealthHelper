package com.example.healthhelper.dietary.interaction.dataclass

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.R
import com.example.healthhelper.dietary.dataclasses.vo.FoodItemVO
import com.example.healthhelper.dietary.dataclasses.vo.FoodVO
import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import com.example.healthhelper.dietary.repository.SelectedFoodItemsRepository
import com.example.healthhelper.dietary.viewmodel.FoodViewModel
import kotlinx.coroutines.launch

fun UpdateSelectedFoodItemVOs(
    context: Context,
    foodItemVOs: List<FoodItemVO>,
    foodViewModel: FoodViewModel,
) {
    val TAG = "tag_UpdateSelectedFoodItemVOs"

    Log.e(TAG,"In UpdateSelectedFoodItemVOs function, foodItemVOs:${foodItemVOs}")
    val foodNames = mutableListOf<String>()
    foodViewModel.viewModelScope.launch {
        foodItemVOs.forEach { foodItemVO ->
            val targetFoodVO = FoodVO()
            targetFoodVO.foodID = foodItemVO.foodID
            val foodName = foodViewModel.selectFoodNameByFoodId(targetFoodVO)
            if(!foodNames.contains(foodName)) {
                foodNames.add(foodName)
            }
        }

        Log.e(TAG,"In UpdateSelectedFoodItemVOs function, foodNames:${foodNames}")

        val selectedFoodItemVOs = mutableListOf<SelectedFoodItemVO>()
        foodNames.forEach{ foodName ->
            val selectedFoodItemVO = SelectedFoodItemVO(mutableStateOf(foodName))
            selectedFoodItemVOs.add(selectedFoodItemVO)
        }

        Log.e(TAG,"In UpdateSelectedFoodItemVOs function, selectedFoodItemVOs:${selectedFoodItemVOs}")

        selectedFoodItemVOs.forEach{ selectedFoodItemVO ->
            SelectedFoodItemsRepository.setCheckingWhenSelectionState(selectedFoodItemVO,true)
        }

        Toast.makeText(context,context.getString(R.string.update_status_of_food_items_successfully),
            Toast.LENGTH_SHORT).show()
    }
}