package com.example.healthhelper.planpage.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.planpage.data.PlanRepository
import com.example.healthhelper.planpage.data.model.DeletePlanModel
import com.example.healthhelper.planpage.data.Result as ApiResult
import com.example.healthhelper.planpage.data.model.PlanModel
import com.example.healthhelper.planpage.data.remote.DependencyProvider
import com.example.healthhelper.planpage.ui.usecase.FinishState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ManagePlanUiState {
    data object Idle : ManagePlanUiState()
    data object Loading : ManagePlanUiState()
    data object Success : ManagePlanUiState()
    data class Error(val message: String) : ManagePlanUiState()
}

class ManagePlanViewModel(
    private val planRepository: PlanRepository,
): ViewModel(){

    private val currentUserId = DependencyProvider.getUserId
    private val _managePlanState = MutableStateFlow<ApiResult<List<PlanModel>>>(ApiResult.Loading)
    val managePlanState: StateFlow<ApiResult<List<PlanModel>>> = _managePlanState.asStateFlow()

    private val _deletePlanState = MutableStateFlow<ManagePlanUiState>(ManagePlanUiState.Idle)
    val  deletePlanState: StateFlow<ManagePlanUiState> = _deletePlanState.asStateFlow()

    init {
        loadUserPlanList(false)
    }

    private fun loadUserPlanList(getRefresh: Boolean) {

        if (_managePlanState.value !is ApiResult.Loading || getRefresh) { // 如果是強制刷新，也顯示Loading
            _managePlanState.value = ApiResult.Loading
        }

        viewModelScope.launch {
            when(val result = planRepository.fetchUserPlans(currentUserId,getRefresh)){
                is ApiResult.Success -> _managePlanState.value = ApiResult.Success(result.data)
                is ApiResult.Error -> _managePlanState.value = ApiResult.Error(result.exception, result.message ?: "Unknown error")
                is ApiResult.Loading -> {}
            }
        }
    }

    fun deletePlan(userDietPlanId: Int, finishState: Int){
        viewModelScope.launch {
            _deletePlanState.value = ManagePlanUiState.Loading

            val state: FinishState? = FinishState.fromInt(finishState)

            if (currentUserId < 0){
                _deletePlanState.value = ManagePlanUiState.Error("使用者ID錯誤")
                return@launch
            }
            if(userDietPlanId <= 0){
                _deletePlanState.value = ManagePlanUiState.Error("計畫ID錯誤")
                return@launch
            }
            if(state == null || state != FinishState.Delete){
                _deletePlanState.value = ManagePlanUiState.Error("錯誤的計畫狀態")
                return@launch
            }

            val getData = DeletePlanModel(
                userId = currentUserId,
                userDietPlanId = userDietPlanId,
                finishstate = finishState
            )

            when(val result = planRepository.deletePlan(getData)) {
                is ApiResult.Success -> {
                    if (result.data.result){
                        _deletePlanState.value = ManagePlanUiState.Success
                    }else{
                        _deletePlanState.value = ManagePlanUiState.Error(result.data.errMsg ?: "刪除計劃失敗，但未收到後端錯誤訊息")
                    }
                }
                is ApiResult.Error -> {
                    _deletePlanState.value = ManagePlanUiState.Error(result.message ?: "Unknown error")
                }

                is ApiResult.Loading -> {
                    _deletePlanState.value = ManagePlanUiState.Loading
                }
            }
        }
    }

    // 手動刷新
    fun refreshUserPlans() {
        loadUserPlanList(getRefresh = true)
    }

    fun resetDeletePlanState() {
        _deletePlanState.value = ManagePlanUiState.Idle
    }
}