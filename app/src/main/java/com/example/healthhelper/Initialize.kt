package com.example.healthhelper

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.dietary.viewmodel.SelectedFoodItemsViewModel
import kotlinx.coroutines.launch

@Composable
fun Initialize() {
    InitRepositoryData()
}

@Composable
fun InitRepositoryData(
    selectedFoodItemsViewModel: SelectedFoodItemsViewModel = viewModel(),
){
    val TAG = "tag_InitRepositoryData"
    Log.e(TAG,"InitRepositoryData was called.")
    LaunchedEffect(Unit) {
        Log.e(TAG,"InitRepositoryData was LaunchedEffected.")
        this.launch {
            selectedFoodItemsViewModel.setRepositoryStateFlow()
        }
        Log.e(TAG,"InitRepositoryData was finished LaunchedEffected .")
    }
}