package com.example.healthhelper.planpage.data.remote

import com.example.healthhelper.planpage.data.model.AddPlanModel
import com.example.healthhelper.planpage.data.model.DeletePlanModel
import com.example.healthhelper.planpage.data.model.GenericApiResponse
import com.example.healthhelper.planpage.data.model.PlanModel
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path

interface PlanApiService {

    @POST("Plan/SelectAllPlan")
    suspend fun getPlansByUserId(@Body userId: UserId): Response<List<PlanModel>>

    @POST("Plan/AddPlan") // 假設後端端點是 "Plan/CreatePlan"
    suspend fun createPlan(@Body addPlan: AddPlanModel): Response<GenericApiResponse> // 或者 Re

    @POST("Plan/DeletePlan")
    suspend fun deletePlan(@Body deletePlan: DeletePlanModel): Response<GenericApiResponse>
}

@Serializable
data class UserId(val userId: Int)
