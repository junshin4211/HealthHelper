package com.example.healthhelper.planpage.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.planpage.data.PlanRepository
import com.example.healthhelper.planpage.data.Result
import com.example.healthhelper.planpage.data.model.AddPlanModel
import com.example.healthhelper.signuplogin.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.healthhelper.planpage.data.Result as ApiResult

class AddPlanViewModel(
    private val planRepository: PlanRepository,
    private val currentUserId : UserManager
):ViewModel(){
//    private val currentUserId = UserManager.getUser().userId

    private val _addPlanState = MutableStateFlow<ApiResult<AddPlanModel>>(Result.Loading)
    val addPlanState: StateFlow<ApiResult<AddPlanModel>> = _addPlanState.asStateFlow()

    fun submitAddPlan(addPlanData: AddPlanModel){
        viewModelScope.launch {

        }
    }
}