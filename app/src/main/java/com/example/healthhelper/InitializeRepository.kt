package com.example.healthhelper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import com.example.healthhelper.dietary.repository.SelectedFoodItemsRepository
import com.example.healthhelper.dietary.viewmodel.SelectedFoodItemsViewModel

@Composable
fun InitializeRepository(
    selectedFoodItemsViewModel: SelectedFoodItemsViewModel = viewModel(),
){
    val TAG = "tag_InitializeRepository"

    LaunchedEffect(Unit) {
        // Fetch data from database and set it to stateflow in SelectedFoodItemsRepository.
        val foodNameVOs = selectedFoodItemsViewModel.fetchDataFromWebRequest()
        val selectedFoodItemVOs = foodNameVOs.map{ foodNameVO ->
            SelectedFoodItemVO(name = mutableStateOf(foodNameVO.foodName))
        }
        SelectedFoodItemsRepository.setData(newData = selectedFoodItemVOs.toMutableList())
    }
}