package com.example.healthhelper.planpage.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.healthhelper.planpage.data.PlanRepository
import com.example.healthhelper.planpage.data.remote.DependencyProvider
import com.example.healthhelper.signuplogin.UserManager

class AppViewModelFactory(
    private val planRepository: PlanRepository = DependencyProvider.planRepository,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PlanMainViewModel::class.java) -> {
                PlanMainViewModel(planRepository) as T
            }

            modelClass.isAssignableFrom(AddPlanViewModel::class.java) -> {
                AddPlanViewModel(planRepository) as T
            }

            modelClass.isAssignableFrom(ManagePlanViewModel::class.java) -> {
                ManagePlanViewModel(planRepository) as T
            }

            modelClass.isAssignableFrom(PlanDetailViewModel::class.java) -> {
                PlanDetailViewModel(planRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
