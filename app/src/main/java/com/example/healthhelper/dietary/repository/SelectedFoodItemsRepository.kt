package com.example.healthhelper.dietary.repository

import com.example.healthhelper.dietary.dataclasses.vo.SelectedFoodItemVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object SelectedFoodItemsRepository {

    val TAG = "tag_SelectedFoodItemsRepository"

    private val _dataFlow = MutableStateFlow<MutableList<SelectedFoodItemVO>>(fetchData())
    val dataFlow: StateFlow<List<SelectedFoodItemVO>> = _dataFlow.asStateFlow()

    private fun fetchData():MutableList<SelectedFoodItemVO> {
        val foodItems = mutableListOf<SelectedFoodItemVO>(
            SelectedFoodItemVO("Apple", 20.0),
            SelectedFoodItemVO("Banana", 30.0)
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

    fun setSelectedState(name: String,state:Boolean){
        _dataFlow.value.find { it.name == name }?.isSelected?.value = state
    }

    fun setSelectedState(selectedFoodItemVO: SelectedFoodItemVO,state:Boolean){
        _dataFlow.value.find { it == selectedFoodItemVO }?.let{it.isSelected.value = state}
    }

    fun remove(selectedFoodItemVO: SelectedFoodItemVO){
        _dataFlow.value.remove(selectedFoodItemVO)
    }

    fun removeAll(selectedFoodItemVOs: List<SelectedFoodItemVO>){
        _dataFlow.value.removeAll(selectedFoodItemVOs)
    }

    fun checked(){
        _dataFlow.value.forEach{
            it.isSelected.value = true
        }
    }

    fun checked(selectedFoodItemVO: SelectedFoodItemVO){
       _dataFlow.value.firstOrNull()?.let{it.isSelected.value = true}
    }

    fun unchecked(){
        _dataFlow.value.forEach{
            it.isSelected.value = false
        }
    }

    fun unchecked(selectedFoodItemVO: SelectedFoodItemVO){
        _dataFlow.value.firstOrNull()?.let{it.isSelected.value = false}
    }
}