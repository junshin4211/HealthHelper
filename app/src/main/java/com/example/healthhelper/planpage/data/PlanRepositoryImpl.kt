package com.example.healthhelper.planpage.data

import android.util.Log
import com.example.healthhelper.planpage.data.Result
import com.example.healthhelper.planpage.data.model.AddPlanModel
import com.example.healthhelper.planpage.data.model.DeletePlanModel
import com.example.healthhelper.planpage.data.model.GenericApiResponse
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
    private val tag = "tag_PlanRepo"

    // --- 緩存相關 ---
    private var cachedUserPlans: List<PlanModel>? = null
    private var lastFetchTimestamp: Long = 0L
    private val expireTimeSet: Long = 5 * 60 * 1000

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
    override suspend fun fetchUserPlans(userId: Int, getRefresh: Boolean): ApiResult<List<PlanModel>> {
        return withContext(ioDispatcher) {
            ApiResult.Loading

            val currentTime = System.currentTimeMillis()

            if (!getRefresh && cachedUserPlans != null && currentTime - lastFetchTimestamp < expireTimeSet) {
                Log.d(tag, "從緩存獲取數據")
                return@withContext ApiResult.Success(cachedUserPlans!!)
            }

            try {
                val request = UserId(userId) // 包裝 userId
                val response = planApiService.getPlansByUserId(request)
                val apiResponse = response.body()
                if(response.isSuccessful){
                    if(apiResponse != null){
                        // 更新緩存
                        synchronized(this@PlanRepositoryImpl) { // 使用 PlanRepositoryImpl 實例作為鎖對象
                            cachedUserPlans = apiResponse
                            lastFetchTimestamp = currentTime // 使用當前請求的時間戳
                        }
                        ApiResult.Success(apiResponse)
                    }else{
                        synchronized(this@PlanRepositoryImpl) { // 使用 PlanRepositoryImpl 實例作為鎖對象
                            cachedUserPlans = emptyList()
                            lastFetchTimestamp = currentTime // 使用當前請求的時間戳
                        }
                        ApiResult.Error(Exception("Http 請求成功 但Body為空"),"取得空計畫")
                    }
                }else{
                    // HTTP 請求本身失敗 (例如 4xx, 5xx 錯誤)
                    val errorMessage = "計劃取得失敗: HTTP ${response.code()}"
                    Log.d(tag,errorMessage)
                    ApiResult.Error(Exception(errorMessage),errorMessage)
                }
            } catch (e: IOException) {
                ApiResult.Error(e, "網路連線異常")
            } catch (e: Exception) {
                ApiResult.Error(e, "發生未知錯誤: ${e.message}")
            }
        }
    }

    override suspend fun addPlan(addPlanData: AddPlanModel): Result<GenericApiResponse> {
        return withContext(ioDispatcher){
            try {
                ApiResult.Loading
                val response = planApiService.createPlan(addPlanData)
                if(response.isSuccessful){
                    val apiResponse = response.body()
                    if(apiResponse != null){
                        if (apiResponse.result){
                            // Http 請求成功(200)
                            Log.d(tag,"新增計畫成功: $apiResponse")
                            ApiResult.Success(apiResponse)
                        }else{
                            // Http 請求成功,但是後端返回的 result 為 false
                            val errorMessage = apiResponse.errMsg ?: "未知錯誤"
                            Log.d(tag,"新增計畫失敗: $errorMessage")
                            ApiResult.Error(Exception("新增計畫失敗"),errorMessage)
                        }
                    }else{
                        Log.d(tag,"Http 請求成功 但Body為空")
                        ApiResult.Error(Exception("Http 請求成功 但Body為空"),"伺服器回應無效")
                    }
                }else{
                    // HTTP 請求本身失敗 (例如 4xx, 5xx 錯誤)
                    val errorMessage = "創建計劃失敗: HTTP ${response.code()}"
                    Log.d(tag,errorMessage)
                    ApiResult.Error(Exception(errorMessage),errorMessage)
                }
            }catch (e:IOException){
                Log.d(tag,"網路連線異常: ${e.message}")
                ApiResult.Error(e,"網路連線異常")
            }catch (e: Exception) { // 捕獲其他所有類型的異常，例如序列化異常
                Log.e(tag, "新增計畫失敗 (未知錯誤): ${e.message}", e)
                ApiResult.Error(e, "發生未知錯誤: ${e.message}") // <--- 返回 Result.Error
            }
        }
    }

    override suspend fun deletePlan(deletePlanData: DeletePlanModel): ApiResult<GenericApiResponse> {
        return withContext(ioDispatcher){
            try {
                ApiResult.Loading
                val response = planApiService.deletePlan(deletePlanData)
                if(response.isSuccessful){
                    val apiResponse = response.body()
                    if(apiResponse != null){
                        if (apiResponse.result){
                            // Http 請求成功(200)
                            Log.d(tag,"刪除計畫成功: $apiResponse")
                            ApiResult.Success(apiResponse)
                        }else{
                            // Http 請求成功,但是後端返回的 result 為 false
                            val errorMessage = apiResponse.errMsg ?: "未知錯誤"
                            Log.d(tag,"刪除計畫失敗: $errorMessage")
                            ApiResult.Error(Exception("刪除計畫失敗"),errorMessage)
                        }
                    }else{
                        Log.d(tag,"Http 請求成功 但Body為空")
                        ApiResult.Error(Exception("Http 請求成功 但Body為空"),"伺服器回應無效")
                    }
                }else{
                    // HTTP 請求本身失敗 (例如 4xx, 5xx 錯誤)
                    val errorMessage = "刪除計劃失敗: HTTP ${response.code()}"
                    Log.d(tag,errorMessage)
                    ApiResult.Error(Exception(errorMessage),errorMessage)
                }
            }catch (e:IOException){
                Log.d(tag,"網路連線異常: ${e.message}")
                ApiResult.Error(e,"網路連線異常")
            }catch (e: Exception) { // 捕獲其他所有類型的異常，例如序列化異常
                Log.e(tag, "刪除計畫失敗 (未知錯誤): ${e.message}", e)
                ApiResult.Error(e, "發生未知錯誤: ${e.message}") // <--- 返回 Result.Error
            }
        }
    }

    override suspend fun invalidateCache() {
        withContext(ioDispatcher) {
            synchronized(this@PlanRepositoryImpl) {
                cachedUserPlans = null
                lastFetchTimestamp = 0L
                Log.d(tag, "Plans cache invalidated.")
            }
        }
    }


    // TODO... 其他 Repository 方法的實現
}
