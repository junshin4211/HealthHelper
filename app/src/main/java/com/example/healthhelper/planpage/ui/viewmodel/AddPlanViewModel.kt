package com.example.healthhelper.planpage.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.planpage.data.PlanRepository
import com.example.healthhelper.planpage.data.Result
import com.example.healthhelper.planpage.data.model.AddPlanModel
import com.example.healthhelper.planpage.domain.usecase.formatMillisToISO
import com.example.healthhelper.planpage.ui.viewmodel.AddPlanUiState.*
import com.example.healthhelper.signuplogin.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class AddPlanUiState {
    data object Idle : AddPlanUiState()
    data object Loading : AddPlanUiState()
    data object Success : AddPlanUiState()
    data class Error(val message: String) : AddPlanUiState()
}

class AddPlanViewModel(
    private val planRepository: PlanRepository,
):ViewModel(){

    private val _addPlanState = MutableStateFlow<AddPlanUiState>(Idle)
    val addPlanState: StateFlow<AddPlanUiState> = _addPlanState.asStateFlow()

    fun submitPlan(addPlanData: AddPlanModel){
        viewModelScope.launch {
            _addPlanState.value = Loading

            // 開始檢查資料
            if (addPlanData.userId < 0){
                _addPlanState.value = Error("使用者ID錯誤")
                return@launch
            }
            if (addPlanData.categoryId <= 0 ){
                _addPlanState.value = Error("計畫ID錯誤")
                return@launch
            }
            if(addPlanData.finishstate != 0){
                _addPlanState.value = Error("完成狀態錯誤")
                return@launch
            }
            if (addPlanData.fatgoal < 0){
                _addPlanState.value = Error("脂訪目標錯誤")
                return@launch
            }
            if (addPlanData.carbongoal < 0){
                _addPlanState.value = Error("碳水化合物目標錯誤")
                return@launch
            }
            if (addPlanData.proteingoal < 0){
                _addPlanState.value = Error("蛋白質目標錯誤")
                return@launch
            }
            if ((addPlanData.fatgoal + addPlanData.carbongoal + addPlanData.proteingoal).toInt() != 100){
                _addPlanState.value = Error("目標比例錯誤")
                return@launch
            }
            if (addPlanData.Caloriesgoal <= 0){
                _addPlanState.value = Error("卡路里目標錯誤")
                return@launch
            }

            when(val result = planRepository.addPlan(addPlanData)){
                is Result.Success -> {
                    if (result.data.result){
                        _addPlanState.value = Success
                    }else{
                        _addPlanState.value = Error(result.data.errMsg ?: "新增計劃失敗，但未收到後端錯誤訊息")
                    }
                }
                is Result.Error -> {
                    _addPlanState.value = Error(result.message ?: "Unknown error")
                }

                is Result.Loading -> {
                    _addPlanState.value = Loading
                }
            }
        }
    }

    fun refreshAddPlanState(){
        _addPlanState.value = Idle
    }

}