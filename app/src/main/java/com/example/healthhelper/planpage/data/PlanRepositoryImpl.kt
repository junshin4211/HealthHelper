package com.example.healthhelper.planpage.data

import android.util.Log
import com.example.healthhelper.planpage.data.Result
import com.example.healthhelper.planpage.data.model.AddPlanModel
import com.example.healthhelper.planpage.data.model.DeletePlanModel
import com.example.healthhelper.planpage.data.model.DiaryNutritionModel
import com.example.healthhelper.planpage.data.model.GenericApiResponse
import com.example.healthhelper.planpage.data.model.PlanModel
import com.example.healthhelper.planpage.data.remote.PlanApiService
import com.example.healthhelper.planpage.data.remote.RequestDiaryBody
import com.example.healthhelper.planpage.data.remote.RequestPlanBody
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
    private val tag = "tag_PlanRepo"

    // --- 緩存相關 ---
    private var cachedUserPlanList: List<PlanModel>? = null
    private var lastFetchPlanTimestamp: Long = 0L
    private val expireTimeSet: Long = 5 * 60 * 1000

    override fun observeUserPlans(userId: Int): Flow<ApiResult<List<PlanModel>>> = flow {
        emit(ApiResult.Loading) // 開始時發送載入狀態
        try {
            val request = UserId(userId) // 包裝 userId
            val response = planApiService.getPlanListByUserId(request) // 調用 API

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
                    emit(
                        ApiResult.Error(
                            Exception("Response body was null despite success"),
                            "No data received."
                        )
                    )
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
                    emit(
                        ApiResult.Error(
                            HttpException(response),
                            "API Error: ${response.code()} - $errorBody"
                        )
                    )
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
    override suspend fun fetchUserPlans(
        userId: Int,
        getRefresh: Boolean
    ): ApiResult<List<PlanModel>> {
        return withContext(ioDispatcher) {
            ApiResult.Loading

            val currentTime = System.currentTimeMillis()

            if (!getRefresh && cachedUserPlanList != null && currentTime - lastFetchPlanTimestamp < expireTimeSet) {
                Log.d(tag, "從緩存獲取數據")
                return@withContext ApiResult.Success(cachedUserPlanList!!)
            }

            try {
                val request = UserId(userId) // 包裝 userId
                val response = planApiService.getPlanListByUserId(request)
                val apiResponse = response.body()
                if (response.isSuccessful) {
                    if (apiResponse != null) {
                        // 更新緩存
                        synchronized(this@PlanRepositoryImpl) { // 使用 PlanRepositoryImpl 實例作為鎖對象
                            cachedUserPlanList = apiResponse
                            lastFetchPlanTimestamp = currentTime // 使用當前請求的時間戳
                        }
                        ApiResult.Success(apiResponse)
                    } else {
                        synchronized(this@PlanRepositoryImpl) { // 使用 PlanRepositoryImpl 實例作為鎖對象
                            cachedUserPlanList = emptyList()
                            lastFetchPlanTimestamp = currentTime // 使用當前請求的時間戳
                        }
                        ApiResult.Error(Exception("Http 請求成功 但Body為空"), "取得空計畫")
                    }
                } else {
                    // HTTP 請求本身失敗 (例如 4xx, 5xx 錯誤)
                    val errorMessage = "計劃取得失敗: HTTP ${response.code()}"
                    Log.d(tag, errorMessage)
                    ApiResult.Error(Exception(errorMessage), errorMessage)
                }
            } catch (e: IOException) {
                ApiResult.Error(e, "網路連線異常")
            } catch (e: Exception) {
                ApiResult.Error(e, "發生未知錯誤: ${e.message}")
            }
        }
    }

    override suspend fun addPlan(addPlanData: AddPlanModel): Result<GenericApiResponse> {
        return withContext(ioDispatcher) {
            try {
                ApiResult.Loading
                val response = planApiService.createPlan(addPlanData)
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null) {
                        if (apiResponse.result) {
                            // Http 請求成功(200)
                            Log.d(tag, "新增計畫成功: $apiResponse")
                            ApiResult.Success(apiResponse)
                        } else {
                            // Http 請求成功,但是後端返回的 result 為 false
                            val errorMessage = apiResponse.errMsg ?: "未知錯誤"
                            Log.d(tag, "新增計畫失敗: $errorMessage")
                            ApiResult.Error(Exception("新增計畫失敗"), errorMessage)
                        }
                    } else {
                        Log.d(tag, "Http 請求成功 但Body為空")
                        ApiResult.Error(Exception("Http 請求成功 但Body為空"), "伺服器回應無效")
                    }
                } else {
                    // HTTP 請求本身失敗 (例如 4xx, 5xx 錯誤)
                    val errorMessage = "創建計劃失敗: HTTP ${response.code()}"
                    Log.d(tag, errorMessage)
                    ApiResult.Error(Exception(errorMessage), errorMessage)
                }
            } catch (e: IOException) {
                Log.d(tag, "網路連線異常: ${e.message}")
                ApiResult.Error(e, "網路連線異常")
            } catch (e: Exception) { // 捕獲其他所有類型的異常，例如序列化異常
                Log.e(tag, "新增計畫失敗 (未知錯誤): ${e.message}", e)
                ApiResult.Error(e, "發生未知錯誤: ${e.message}") // <--- 返回 Result.Error
            }
        }
    }

    override suspend fun deletePlan(deletePlanData: DeletePlanModel): ApiResult<GenericApiResponse> {
        return withContext(ioDispatcher) {
            try {
                ApiResult.Loading
                val response = planApiService.deletePlan(deletePlanData)
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null) {
                        if (apiResponse.result) {
                            // Http 請求成功(200)
                            Log.d(tag, "刪除計畫成功: $apiResponse")
                            ApiResult.Success(apiResponse)
                        } else {
                            // Http 請求成功,但是後端返回的 result 為 false
                            val errorMessage = apiResponse.errMsg ?: "未知錯誤"
                            Log.d(tag, "刪除計畫失敗: $errorMessage")
                            ApiResult.Error(Exception("刪除計畫失敗"), errorMessage)
                        }
                    } else {
                        Log.d(tag, "Http 請求成功 但Body為空")
                        ApiResult.Error(Exception("Http 請求成功 但Body為空"), "伺服器回應無效")
                    }
                } else {
                    // HTTP 請求本身失敗 (例如 4xx, 5xx 錯誤)
                    val errorMessage = "刪除計劃失敗: HTTP ${response.code()}"
                    Log.d(tag, errorMessage)
                    ApiResult.Error(Exception(errorMessage), errorMessage)
                }
            } catch (e: IOException) {
                Log.d(tag, "網路連線異常: ${e.message}")
                ApiResult.Error(e, "網路連線異常")
            } catch (e: Exception) { // 捕獲其他所有類型的異常，例如序列化異常
                Log.e(tag, "刪除計畫失敗 (未知錯誤): ${e.message}", e)
                ApiResult.Error(e, "發生未知錯誤: ${e.message}") // <--- 返回 Result.Error
            }
        }
    }

    override suspend fun fetchSinglePlan(
        queryPlanData: RequestPlanBody,
        getRefresh: Boolean
    ): ApiResult<PlanModel> {
        return withContext(ioDispatcher) {
            // 1. 檢查列表緩存 (cachedUserPlanList) 是否有效且計劃存在 (如果允許從列表緩存快速返回)
            //    這一步是可選的，取決於你是否希望在列表緩存有效時，避免調用單獨的 API。
            //    如果 PlanDetailScreen 總需要最新數據，或者數據結構與 PlanModel 不同，可以跳過這一步。
            val currentTime = System.currentTimeMillis()
            if (!getRefresh && cachedUserPlanList != null && currentTime - lastFetchPlanTimestamp < expireTimeSet) {
                val cachedPlan =
                    cachedUserPlanList?.find { it.userDietPlanId == queryPlanData.userDietPlanId }
                if (cachedPlan != null) {
                    Log.d(
                        tag,
                        "從列表緩存獲取單個計劃數據 (planId: ${queryPlanData.userDietPlanId})"
                    )
                    return@withContext ApiResult.Success(cachedPlan)
                }
                Log.d(
                    tag,
                    "計劃 (planId: ${queryPlanData.userDietPlanId}) 在有效的列表緩存中未找到。"
                )
            }

            // 2. 如果不從列表緩存獲取，或者列表緩存無效/需要刷新/未找到，則調用專門的 API
            Log.d(
                tag,
                "準備從網絡獲取單個計劃 (planId: ${queryPlanData.userDietPlanId})。強制刷新: $getRefresh"
            )
            ApiResult.Loading // 可以考慮在實際網絡請求前發出 Loading 狀態，如果 ViewModel 會處理它的話

            try {
                // 使用你 PlanApiService 中的方法
                // 確保 PlanApiService 中的 @POST 註解有正確的端點路徑
                val response = planApiService.getSinglePlan(queryPlanData) // 傳入 queryPlanData

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null) {
                        Log.d(tag, "成功從 API 獲取單個計劃: $apiResponse")
                        // 可選：用獲取到的最新單個計劃數據更新 cachedUserPlanList 中的對應條目
                        ApiResult.Success(apiResponse)
                    } else {
                        Log.e(
                            tag,
                            "獲取單個計劃 API 成功，但響應體為空 (planId: ${queryPlanData.userDietPlanId})"
                        )
                        ApiResult.Error(Exception("API response body was null"), "伺服器回應無效")
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(
                        tag,
                        "獲取單個計劃 API 失敗: HTTP ${response.code()} - $errorBody (planId: ${queryPlanData.userDietPlanId})"
                    )
                    ApiResult.Error(
                        HttpException(response),
                        "獲取計劃詳情失敗: HTTP ${response.code()}"
                    )
                }
            } catch (e: IOException) {
                Log.e(tag, "獲取單個計劃網路異常 (planId: ${queryPlanData.userDietPlanId})", e)
                ApiResult.Error(e, "網路連線異常")
            } catch (e: Exception) { // 例如 kotlinx.serialization.SerializationException
                Log.e(tag, "獲取單個計劃發生未知錯誤 (planId: ${queryPlanData.userDietPlanId})", e)
                ApiResult.Error(e, "發生未知錯誤: ${e.message}")
            }
        }
    }

    override suspend fun fetchDiaryList(requestDiaryBody: RequestDiaryBody): ApiResult<List<DiaryNutritionModel>> {
        return withContext(ioDispatcher) {

            ApiResult.Loading

            try {
                val response = planApiService.getDiaryList(requestDiaryBody)
                val apiResponse = response.body()

                if (response.isSuccessful) {
                    if (apiResponse != null) {
                        ApiResult.Success(apiResponse)
                    } else {
                        ApiResult.Error(Exception("Http 請求成功 但Body為空"), "取得空計畫")
                    }
                } else {
                    // HTTP 請求本身失敗 (例如 4xx, 5xx 錯誤)
                    val errorMessage = "計劃取得失敗: HTTP ${response.code()}"
                    Log.d(tag, errorMessage)
                    ApiResult.Error(Exception(errorMessage), errorMessage)
                }

            } catch (e: IOException) {
                ApiResult.Error(e, "網路連線異常")
            } catch (e: Exception) {
                ApiResult.Error(e, "發生未知錯誤: ${e.message}")
            }
        }
    }

    override suspend fun invalidateCache() {
        withContext(ioDispatcher) {
            synchronized(this@PlanRepositoryImpl) {
                cachedUserPlanList = null
                lastFetchPlanTimestamp = 0L
                Log.d(tag, "Plans cache invalidated.")
            }
        }
    }


    // TODO... 其他 Repository 方法的實現
}
