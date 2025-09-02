package com.example.healthhelper.planpage.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.planpage.data.PlanRepository
import com.example.healthhelper.planpage.data.model.PlanModel
import com.example.healthhelper.planpage.data.remote.DependencyProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.healthhelper.planpage.data.Result as ApiResult // 使用別名

class PlanMainViewModel(
    private val planRepository: PlanRepository,
) : ViewModel() {
    private val currentUserId = DependencyProvider.getUserId
    private val _planMainState = MutableStateFlow<ApiResult<List<PlanModel>>>(ApiResult.Loading)
    val planMainState: StateFlow<ApiResult<List<PlanModel>>> = _planMainState.asStateFlow()

    init {
        loadUserPlans(true)
    }

    private fun loadUserPlans(getRefresh: Boolean) {

        if (_planMainState.value !is ApiResult.Loading || getRefresh) { // 如果是強制刷新，也顯示Loading
            _planMainState.value = ApiResult.Loading
        }
        viewModelScope.launch {

            val result = planRepository.fetchUserPlans(currentUserId,getRefresh)

            when(result){
                is ApiResult.Success -> _planMainState.value = ApiResult.Success(result.data)
                is ApiResult.Error -> _planMainState.value = ApiResult.Error(result.exception, result.message ?: "Unknown error")
                is ApiResult.Loading -> {}
            }
        }
    }

    //如果需要在 ViewModel 初始化時或特定時機觸發一次性載入
    fun refreshUserPlans() {
        viewModelScope.launch {
            planRepository.invalidateCache()
            loadUserPlans(getRefresh = true)
        }
    }

}
