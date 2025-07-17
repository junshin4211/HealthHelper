package com.example.healthhelper.planpage.data.remote

import com.example.healthhelper.planpage.data.PlanRepository
import com.example.healthhelper.planpage.data.PlanRepositoryImpl
import com.example.healthhelper.signuplogin.UserManager
import kotlinx.coroutines.Dispatchers

object DependencyProvider {
    val planApiService: PlanApiService by lazy { NetworkClient.planApiService }
    val planRepository: PlanRepository by lazy { PlanRepositoryImpl(planApiService, Dispatchers.IO) }

    val getUserId: Int by lazy { UserManager.getUser().userId }
}