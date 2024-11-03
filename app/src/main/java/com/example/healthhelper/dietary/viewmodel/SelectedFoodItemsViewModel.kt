package com.example.healthhelper.dietary.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import com.example.healthhelper.dietary.dataclasses.vo.other.MessageVO
import com.example.healthhelper.dietary.gson.gson
import com.example.healthhelper.dietary.repository.SelectedFoodItemsRepository
import com.example.healthhelper.dietary.servlet.url.DietDiaryUrl
import com.example.healthhelper.web.httpGet
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SelectedFoodItemsViewModel: ViewModel(){
    val TAG = "tag_SelectedFoodItemsViewModel"

    private val repository = SelectedFoodItemsRepository
    val data: StateFlow<List<SelectedFoodItemVO>> = repository.dataFlow
    val selectedData: StateFlow<SelectedFoodItemVO> = repository.selectedDataFlow

    fun setRepositoryStateFlow() {
        viewModelScope.launch {
            val url = DietDiaryUrl.listAvailableFoodsNameAndGramsUrl
            val dataIn = httpGet(url = url, dataOut = "")
            Log.d(TAG,"dataIn:${dataIn}")
            val messageVO = gson.fromJson(dataIn, MessageVO::class.java)
            if(messageVO.errorMessage.isBlank()){
                SelectedFoodItemsRepository.setData(mutableListOf())
                return@launch
            }
        }
    }
}