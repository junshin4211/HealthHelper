package com.example.healthhelper.plan

import androidx.annotation.StringRes
import com.example.healthhelper.R

enum class PlanPage(@StringRes val title: Int) {
    DietPlan(title = R.string.plan),
    MyPlan(title = R.string.myplan),
    CompletedPlan(title = R.string.completedplan)
}

enum class Description(@StringRes val title: Int){
    HighProtein(title = R.string.highprotein),
    LowCarb(title = R.string.lowcarb),
    Ketone(title = R.string.ketone),
    Mediterra(title = R.string.mediterra),
    Custom(title = R.string.custom)
}