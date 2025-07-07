package com.example.healthhelper.planpage.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.planpage.data.PlanRepository
import com.example.healthhelper.planpage.data.Result
import com.example.healthhelper.planpage.data.model.PlanModel
import com.example.healthhelper.signuplogin.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.healthhelper.planpage.data.Result as ApiResult // 使用別名

class PlanMainViewModel(
    private val planRepository: PlanRepository,
    // 如果 userId 是動態的 (例如從導航參數獲取)，可以使用 SavedStateHandle
    // private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // 假設有一個固定的 userId 用於演示，實際中可能來自登入資訊或導航參數
    private val currentUserId = UserManager.getUser().userId // ***示例 User ID，請替換為實際邏輯***

    // 使用 StateFlow 暴露計畫列表
//    val userPlans: StateFlow<ApiResult<List<PlanModel>>> =
//        planRepository.observeUserPlans(currentUserId) // 傳入 userId
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5000L),
//                initialValue = ApiResult.Loading
//            )

    // 或者，如果使用一次性獲取：
    private val _planMainState = MutableStateFlow<ApiResult<List<PlanModel>>>(ApiResult.Loading)
    val planMainState: StateFlow<ApiResult<List<PlanModel>>> = _planMainState.asStateFlow()

    fun loadUserPlansOnce(userId: Int) {
        viewModelScope.launch {
            when(val result = planRepository.fetchUserPlans(userId)){
                is ApiResult.Success -> _planMainState.value = Result.Success(result.data)
                is ApiResult.Error -> _planMainState.value = Result.Error(result.exception, result.message ?: "Unknown error")
                is ApiResult.Loading -> {}
            }
        }
    }

    //如果需要在 ViewModel 初始化時或特定時機觸發一次性載入
    init {
        loadUserPlansOnce(currentUserId)
    }

    // ... 其他 ViewModel 邏輯，例如添加、修改、刪除計畫
}
