package com.example.healthhelper.planpage.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.planpage.data.PlanRepository
import com.example.healthhelper.planpage.data.model.DiaryNutritionModel
import com.example.healthhelper.planpage.data.model.PlanModel
import com.example.healthhelper.planpage.data.Result as ApiResult // Alias for Result
import com.example.healthhelper.planpage.data.remote.DependencyProvider
import com.example.healthhelper.planpage.data.remote.RequestDiaryBody
import com.example.healthhelper.planpage.data.remote.RequestPlanBody
import com.example.healthhelper.planpage.domain.usecase.transformDateTimeToDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 1. 定義一個新的狀態類來包含計劃詳情和日記列表
data class PlanDetailScreenState(
    val planDetailResult: ApiResult<PlanModel> = ApiResult.Loading,
    val diaryListResult: ApiResult<List<DiaryNutritionModel>> = ApiResult.Loading,
    val isLoading: Boolean = true, // 綜合加載狀態
    val errorMessage: String? = null // 綜合錯誤信息
)

class PlanDetailViewModel(
    private val planRepository: PlanRepository
) : ViewModel() {
    private val currentUserId = DependencyProvider.getUserId

    // 使用新的組合狀態
    private val _detailScreenState = MutableStateFlow(PlanDetailScreenState())
    val detailScreenState: StateFlow<PlanDetailScreenState> = _detailScreenState.asStateFlow()

    // 原有的 planDetailState 可以移除或設為 private，因為 screenState 會包含它
    // private val _planDetailState = MutableStateFlow<ApiResult<PlanModel>>(ApiResult.Loading)
    // val planDetailState: StateFlow<ApiResult<PlanModel>> = _planDetailState.asStateFlow()


    fun loadPlanAndDiaries(userDietPlanId: Int, getRefresh: Boolean) {
        viewModelScope.launch {
            // 開始時，設置為加載中狀態
            _detailScreenState.value = PlanDetailScreenState(isLoading = true)

            if (userDietPlanId < 0) {
                _detailScreenState.value = PlanDetailScreenState(
                    isLoading = false,
                    planDetailResult = ApiResult.Error(Exception("計畫ID錯誤"), "Invalid userDietPlanId"),
                    errorMessage = "計畫ID錯誤"
                )
                return@launch
            }

            if(currentUserId < 0 ){
                _detailScreenState.value = PlanDetailScreenState(
                    isLoading = false,
                    planDetailResult = ApiResult.Error(Exception("用戶ID錯誤"), "Invalid currentUserId"),
                    errorMessage = "用戶ID錯誤"
                )
                return@launch
            }

            // --- 步驟 1: 獲取計劃詳情 ---
            val queryPlanData = RequestPlanBody(currentUserId, userDietPlanId)
            val planResult = planRepository.fetchSinglePlan(queryPlanData, getRefresh)

            _detailScreenState.value = _detailScreenState.value.copy(planDetailResult = planResult)

            when (planResult) {
                is ApiResult.Success -> {
                    val plan = planResult.data
                    // --- 步驟 2: 使用計劃詳情中的日期去獲取日記列表 ---
                    // 假設 PlanModel 中的 startDateTime 和 endDateTime 是可以直接使用的日期字符串
                    // 如果不是，你需要先進行格式轉換
                    // 例如，如果 PlanModel 的日期是 "MMM d, uuuu, h:mm:ss a" 格式
                    // 而後端 RequestDiaryBody 需要 "yyyy-MM-dd"

                    // **重要: 日期格式轉換**
                    // 你需要確保 PlanModel 中的 startDateTime 和 endDateTime
                    // 能夠被正確轉換成 RequestDiaryBody 所需的格式 ("yyyy-MM-dd")。
                    // 這裡我假設 PlanModel 的日期已經是 "yyyy-MM-dd" 或類似可以直接使用的格式
                    // 如果不是，你需要添加轉換邏輯。
                    // 示例：如果 PlanModel.startDateTime 是 "2024-08-01T10:00:00Z" (ISO 8601 DateTime)
                    // 你需要提取日期部分 "2024-08-01"
                    val startDateForDiary = transformDateTimeToDate(plan.startDateTime)
                    val endDateForDiary = transformDateTimeToDate(plan.endDateTime)

                    if(startDateForDiary == null || endDateForDiary == null){
                        _detailScreenState.value = _detailScreenState.value.copy(
                            isLoading = false,
                            diaryListResult = ApiResult.Error(Exception("日期轉換錯誤"), "計劃日期格式錯誤，無法獲取日記"),
                            errorMessage = "計劃日期格式錯誤"
                        )
                        return@launch
                    }

                    val requestDiaryBody = RequestDiaryBody(
                        userId = currentUserId,
                        startDate = startDateForDiary,
                        endDate = endDateForDiary
                    )
                    val diaryResult = planRepository.fetchDiaryList(requestDiaryBody)

                    _detailScreenState.value = _detailScreenState.value.copy(
                        diaryListResult = diaryResult,
                        isLoading = false, // 所有數據獲取嘗試完成
                        errorMessage = if (diaryResult is ApiResult.Error) diaryResult.message ?: "獲取日記失敗" else null
                    )
                }
                is ApiResult.Error -> {
                    _detailScreenState.value = _detailScreenState.value.copy(
                        isLoading = false,
                        errorMessage = planResult.message ?: "獲取計劃詳情失敗"
                    )
                }
                is ApiResult.Loading -> {
                    // 理論上 planRepository.fetchSinglePlan 不會直接返回 Loading 給 viewModelScope
                    // 但為了完整性
                    _detailScreenState.value = _detailScreenState.value.copy(isLoading = true)
                }
            }
        }
    }

    // 你原有的 loadPlanDetail 可以保留用於只需要計劃詳情的場景，
    // 或者將其邏輯合併到 loadPlanAndDiaries 中。
    // 如果不再單獨使用，可以設為 private 或移除。
    /*
    fun loadPlanDetail(userDietPlanId: Int, getRefresh: Boolean) {
        _planDetailState.value = ApiResult.Loading
        viewModelScope.launch {
            if(userDietPlanId < 0){
                _planDetailState.value = ApiResult.Error(Exception("計畫ID錯誤"), "Invalid userDietPlanId")
                return@launch
            }
            val queryPlanData = RequestPlanBody(currentUserId, userDietPlanId)
            val result = planRepository.fetchSinglePlan(queryPlanData, getRefresh)
            _planDetailState.value = result
        }
    }
    */
}

