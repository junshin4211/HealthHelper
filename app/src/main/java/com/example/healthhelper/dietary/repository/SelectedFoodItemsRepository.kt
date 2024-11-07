package com.example.healthhelper.dietary.repository

import androidx.compose.runtime.mutableStateOf
import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object SelectedFoodItemsRepository {

    val TAG = "tag_SelectedFoodItemsRepository"

    private val _dataFlow = MutableStateFlow(fetchData())
    val dataFlow: StateFlow<List<SelectedFoodItemVO>>
        get() = _dataFlow.asStateFlow()

    private val _selectedDataFlow = MutableStateFlow(fetchData()[0])
    val selectedDataFlow: StateFlow<SelectedFoodItemVO>
        get() = _selectedDataFlow.asStateFlow()

    private fun fetchData():MutableList<SelectedFoodItemVO> {
        val foodItems = mutableListOf(
            SelectedFoodItemVO(name = mutableStateOf("Apple")),
            SelectedFoodItemVO(name = mutableStateOf("Banana")),
            SelectedFoodItemVO(name = mutableStateOf("Grape")),
            SelectedFoodItemVO(name = mutableStateOf("Orange")),
        )
        return foodItems
    }

    fun setData(newData: MutableList<SelectedFoodItemVO>){
        _dataFlow.update { newData }
    }

    fun setSelectedData(newData: SelectedFoodItemVO){
        _selectedDataFlow.update { newData }
    }

    // set meal of selected item.
    fun setSelectedDataMealValue(meal: String){
        _selectedDataFlow.value.meal.value = meal
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

    fun setMealValue(selectedFoodItemVO:SelectedFoodItemVO, meal: String){
        _dataFlow.value.find { it == selectedFoodItemVO }?.let{it.meal.value = meal}
    }

    fun remove(selectedFoodItemVO: SelectedFoodItemVO){
        _dataFlow.value.remove(selectedFoodItemVO)
    }

    fun removeAll(selectedFoodItemVOs: List<SelectedFoodItemVO>){
        _dataFlow.value.removeAll(selectedFoodItemVOs)
    }
}