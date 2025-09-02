package com.example.healthhelper.planpage.data // 與您專案中的路徑一致

import com.example.healthhelper.planpage.data.model.AddPlanModel
import com.example.healthhelper.planpage.data.model.DeletePlanModel
import com.example.healthhelper.planpage.data.model.DiaryNutritionModel
import com.example.healthhelper.planpage.data.model.GenericApiResponse
import com.example.healthhelper.planpage.data.model.PlanModel
import com.example.healthhelper.planpage.data.remote.RequestDiaryBody
import com.example.healthhelper.planpage.data.remote.RequestPlanBody
import kotlinx.coroutines.flow.Flow
import com.example.healthhelper.planpage.data.Result as ApiResult // 使用別名

interface PlanRepository {
    // 獲取特定使用者的計畫列表 (使用 Flow 可以響應式更新，如果不需要可以返回 suspend fun ...(): ApiResult<List<PlanModel>>)
    fun observeUserPlans(userId: Int): Flow<ApiResult<List<PlanModel>>>

    // 或者，一個一次性的獲取函式
    suspend fun fetchUserPlans(userId: Int, getRefresh: Boolean): ApiResult<List<PlanModel>>

    suspend fun addPlan(addPlanData: AddPlanModel): ApiResult<GenericApiResponse>

    suspend fun deletePlan(deletePlanData: DeletePlanModel): ApiResult<GenericApiResponse>

    suspend fun fetchSinglePlan(queryPlanData: RequestPlanBody, getRefresh: Boolean): ApiResult<PlanModel>

    suspend fun fetchDiaryList(requestDiaryBody: RequestDiaryBody): ApiResult<List<DiaryNutritionModel>>

    suspend fun invalidateCache()

}

