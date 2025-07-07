package com.example.healthhelper.planpage.data.remote

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
    //TODO 其他 API 方法
}

@Serializable
data class UserId(val userId: Int)
