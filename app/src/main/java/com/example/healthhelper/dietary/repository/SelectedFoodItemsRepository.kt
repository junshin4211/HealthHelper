package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import com.example.healthhelper.dietary.servlet.url.DietDiaryUrl
import com.example.healthhelper.web.httpGet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object SelectedFoodItemsRepository {

    val TAG = "tag_SelectedFoodItemsRepository"

    private val _dataFlow = MutableStateFlow<MutableList<SelectedFoodItemVO>>(fetchDataFromDatabase())
    val dataFlow: StateFlow<List<SelectedFoodItemVO>> = _dataFlow.asStateFlow()

    private fun fetchData():MutableList<SelectedFoodItemVO> {
        val foodItems = mutableListOf(
            SelectedFoodItemVO(name = "Apple"),
            SelectedFoodItemVO(name = "Banana"),
            SelectedFoodItemVO(name = "Grape"),
            SelectedFoodItemVO(name = "Orange"),
        )
        return foodItems
    }

    private fun fetchDataFromDatabase():MutableList<SelectedFoodItemVO> {
        val foodItems = httpGet(
            url = DietDiaryUrl.
        )
        return foodItems
    }

    fun setData(newData: MutableList<SelectedFoodItemVO>){
        _dataFlow.update { newData }
    }

    fun updateData(selectedFoodItemVO: SelectedFoodItemVO,newSelectedFoodItemVO: SelectedFoodItemVO){
       val elemIndex =  _dataFlow.value.indexOf(selectedFoodItemVO)
       if(elemIndex!=-1){
           _dataFlow.value[elemIndex] = newSelectedFoodItemVO
       }
    }

    fun setCheckedWhenQueryState(selectedFoodItemVO: SelectedFoodItemVO,state:Boolean){
        _dataFlow.value.find { it == selectedFoodItemVO }?.let{it.isCheckedWhenQuery.value = state}
    }

    fun setAllCheckedWhenQueryState(state:Boolean){
        _dataFlow.value.forEach{ it.isCheckedWhenQuery.value = state}
    }

    fun setCheckedWhenSelectionState(selectedFoodItemVO: SelectedFoodItemVO,state:Boolean){
        _dataFlow.value.find { it == selectedFoodItemVO }?.let{it.isCheckedWhenSelection.value = state}
    }

    fun setAllCheckedWhenSelectionState(state:Boolean){
        _dataFlow.value.forEach{ it.isCheckedWhenSelection.value = state}
    }

    fun setCheckingWhenQueryState(selectedFoodItemVO: SelectedFoodItemVO,state:Boolean){
        _dataFlow.value.find { it == selectedFoodItemVO }?.let{it.isCheckingWhenQuery.value = state}
    }

    fun setAllCheckingWhenQueryState(state:Boolean){
        _dataFlow.value.forEach{ it.isCheckingWhenQuery.value = state}
    }

    fun setCheckingWhenSelectionState(selectedFoodItemVO: SelectedFoodItemVO,state:Boolean){
        _dataFlow.value.find { it == selectedFoodItemVO }?.let{it.isCheckingWhenSelection.value = state}
    }

    fun setAllCheckingWhenSelectionState(state:Boolean){
        _dataFlow.value.forEach{ it.isCheckingWhenSelection.value = state}
    }

    fun remove(selectedFoodItemVO: SelectedFoodItemVO){
        _dataFlow.value.remove(selectedFoodItemVO)
    }

    fun removeAll(selectedFoodItemVOs: List<SelectedFoodItemVO>){
        _dataFlow.value.removeAll(selectedFoodItemVOs)
    }
}