package com.example.healthhelper.planpage.data.remote

import com.example.healthhelper.planpage.data.model.AddPlanModel
import com.example.healthhelper.planpage.data.model.DeletePlanModel
import com.example.healthhelper.planpage.data.model.DiaryNutritionModel
import com.example.healthhelper.planpage.data.model.GenericApiResponse
import com.example.healthhelper.planpage.data.model.PlanModel
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PlanApiService {

    @POST("Plan/SelectAllPlan")
    suspend fun getPlanListByUserId(@Body userId: UserId): Response<List<PlanModel>>

    @POST("Plan/AddPlan")
    suspend fun createPlan(@Body addPlan: AddPlanModel): Response<GenericApiResponse>

    @POST("Plan/DeletePlan")
    suspend fun deletePlan(@Body deletePlan: DeletePlanModel): Response<GenericApiResponse>

    @POST("Plan/SelectSinglePlan")
    suspend fun getSinglePlan(@Body requestPlanBody: RequestPlanBody): Response<PlanModel>

    @POST("Plan/DiaryList")
    suspend fun getDiaryList(@Body requestDiaryBody: RequestDiaryBody): Response<List<DiaryNutritionModel>>
}

@Serializable
data class UserId(val userId: Int)

@Serializable
data class RequestPlanBody(val userId: Int, val userDietPlanId: Int)

@Serializable
data class RequestDiaryBody(val userId: Int, val startDate: String, val endDate: String)