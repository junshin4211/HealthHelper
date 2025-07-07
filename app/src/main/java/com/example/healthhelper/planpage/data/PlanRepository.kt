package com.example.healthhelper.planpage.data // 與您專案中的路徑一致

import com.example.healthhelper.planpage.data.model.PlanModel
import kotlinx.coroutines.flow.Flow
import com.example.healthhelper.planpage.data.Result as ApiResult // 使用別名

interface PlanRepository {
    // 獲取特定使用者的計畫列表 (使用 Flow 可以響應式更新，如果不需要可以返回 suspend fun ...(): ApiResult<List<PlanModel>>)
    fun observeUserPlans(userId: Int): Flow<ApiResult<List<PlanModel>>>

    // 或者，一個一次性的獲取函式
    suspend fun fetchUserPlans(userId: Int): ApiResult<List<PlanModel>>

    // TODO... 其他 Repository interface方法
}

