package com.example.healthhelper.planpage.data

import com.example.healthhelper.planpage.data.model.PlanModel
import com.example.healthhelper.planpage.data.remote.PlanApiService
import com.example.healthhelper.planpage.data.remote.UserId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import com.example.healthhelper.planpage.data.Result as ApiResult // 使用別名

class PlanRepositoryImpl(
    private val planApiService: PlanApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PlanRepository {

    override fun observeUserPlans(userId: Int): Flow<ApiResult<List<PlanModel>>> = flow {
        emit(ApiResult.Loading) // 開始時發送載入狀態
        try {
            val request = UserId(userId) // 包裝 userId
            val response = planApiService.getPlansByUserId(request) // 調用 API

            if (response.isSuccessful) {
                val plans = response.body()
                if (plans != null) {
                    emit(ApiResult.Success(plans))
                } else {
                    // 這種情況理論上不應該發生，如果 isSuccessful 為 true，body 通常不為 null，
                    // 除非後端真的返回了一個空的成功響應。
                    // 或者，如果後端在 plans 為空時返回的是 "the plan is null" 字符串，
                    // 並且 Content-Type 仍然是 application/json，
                    // Kotlinx Serialization 解析 "the plan is null" 為 List<PlanWithCategory> 會失敗。
                    // 這種情況下，您可能需要使用 ResponseBody 並手動檢查內容。
                    emit(ApiResult.Error(Exception("Response body was null despite success"), "No data received."))
                }
            } else {
                // HTTP 錯誤 (4xx, 5xx)
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                // 檢查後端是否在 getallplan 為 null 時返回了 "the plan is null" 並且狀態碼不是 2xx
                if (response.code() != 200 && errorBody.contains("the plan is null")) {
                    // 特殊處理：後端返回 "the plan is null" 字符串，且非成功狀態碼
                    // 可以視為一種"成功但無數據"的情況，或者一種特定的業務錯誤
                    emit(ApiResult.Success(emptyList())) // 或者一個自定義的 Error 類型
                } else {
                    emit(ApiResult.Error(HttpException(response), "API Error: ${response.code()} - $errorBody"))
                }
            }
        } catch (e: IOException) {
            emit(ApiResult.Error(e, "Network error. Please check your connection."))
        } catch (e: Exception) { // 例如 kotlinx.serialization.SerializationException
            // 如果後端在計畫為空時返回 "the plan is null" 字符串，但 Content-Type 是 application/json，
            // 且 Retrofit 嘗試將其解析為 List<PlanWithCategory>，這裡可能會捕獲到 SerializationException。
            if (e.message?.contains("Cannot deserialize string") == true && e.message?.contains("the plan is null") == true) {
                // 特殊處理：後端返回了 "the plan is null" 字符串，導致解析失敗
                // 這裡我們假設這種情況下應該返回一個空列表
                emit(ApiResult.Success(emptyList()))
            } else {
                emit(ApiResult.Error(e, "An unexpected error occurred: ${e.message}"))
            }
        }
    }.flowOn(ioDispatcher)

    // 一次性獲取函式的實現
    override suspend fun fetchUserPlans(userId: Int): ApiResult<List<PlanModel>> {
        return withContext(ioDispatcher) {
            try {
                val request = UserId(userId) // 包裝 userId
                val response = planApiService.getPlansByUserId(request)
                val plans = response.body()
                if(plans != null){
                    ApiResult.Success(plans)
                }else{
                    ApiResult.Error(Exception("Response body was null despite success"), "No data received.")
                }
            } catch (e: IOException) {
                ApiResult.Error(e, "Network error fetching user plans.")
            } catch (e: Exception) {
                ApiResult.Error(e, "Failed to fetch user plans: ${e.message}")
            }
        }
    }

    // TODO... 其他 Repository 方法的實現
}
