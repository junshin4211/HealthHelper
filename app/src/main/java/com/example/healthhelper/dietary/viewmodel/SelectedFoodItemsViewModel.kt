package com.example.healthhelper.dietary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import com.example.healthhelper.dietary.gson.gson
import com.example.healthhelper.dietary.repository.SelectedFoodItemsRepository
import com.example.healthhelper.dietary.servlet.url.DietDiaryUrl
import com.example.healthhelper.web.httpPost
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SelectedFoodItemsViewModel: ViewModel(){
    private val repository = SelectedFoodItemsRepository
    val data: StateFlow<List<SelectedFoodItemVO>> = repository.dataFlow

    fun getText() {
        viewModelScope.launch {
            val url = DietDiaryUrl.listAvailableFoodsNameAndGramsUrl
            val dataIn = httpPost(url = url, dataOut = "")
            val foodItems = gson.fromJson<(dataIn, jsonObject)
        }
    }
}