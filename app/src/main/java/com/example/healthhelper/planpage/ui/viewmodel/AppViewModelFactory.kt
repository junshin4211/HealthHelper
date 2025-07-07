package com.example.healthhelper.planpage.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.healthhelper.planpage.data.remote.NetworkClient
import com.example.healthhelper.planpage.data.PlanRepository
import com.example.healthhelper.planpage.data.PlanRepositoryImpl
import kotlinx.coroutines.Dispatchers

class PlanViewModelFactory : ViewModelProvider.Factory {

    private fun providePlanRepository(): PlanRepository {
        return PlanRepositoryImpl(
            planApiService = NetworkClient.planApiService,
            ioDispatcher = Dispatchers.IO
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlanMainViewModel::class.java)) {
            return PlanMainViewModel(providePlanRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
