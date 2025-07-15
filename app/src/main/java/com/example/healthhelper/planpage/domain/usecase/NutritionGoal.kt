package com.example.healthhelper.planpage.domain.usecase

import androidx.annotation.StringRes
import com.example.healthhelper.R
import com.example.healthhelper.plan.PlanPage

object NutritionGoal {
    //(fat,carb,protein)
    private val goalsMap = mapOf(
        R.string.highProtein to Triple(0.3f, 0.3f, 0.4f),
        R.string.lowCarbHydrate to Triple(0.5f, 0.2f, 0.3f),
        R.string.ketone to Triple(0.75f, 0.05f, 0.2f),
        R.string.mediterra to Triple(0.4f, 0.45f, 0.15f),
    )

    fun getGoals(@StringRes plan: Int): Triple<Float, Float, Float>? {
        return goalsMap[plan]
    }
}