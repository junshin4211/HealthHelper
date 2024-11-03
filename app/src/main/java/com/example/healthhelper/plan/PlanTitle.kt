package com.example.healthhelper.plan

import android.content.Context
import androidx.annotation.StringRes
import com.example.healthhelper.R

enum class PlanPage(
    @StringRes val title: Int,
){
    DietPlan(title = R.string.plan),
    MyPlan(title = R.string.myplan),
    CompletedPlan(title = R.string.completedplan),
    ManagePlan(title = R.string.manageplan),
    CheckPlan(title = R.string.checkplan),
    HighProtein(title = R.string.highprotein),
    LowCarb(title = R.string.lowcarb),
    Ketone(title = R.string.ketone),
    Mediterra(title = R.string.mediterra),
    Custom(title = R.string.custom);

    fun getPlanTitle(context: Context):String{
        return context.getString(title)
    }
}

object NutritionGoals {
    //(fat,carb,protein)
    private val goalsMap = mapOf(
        PlanPage.HighProtein to Triple(30f, 30f, 40f),
        PlanPage.LowCarb to Triple(50f, 20f, 30f),
        PlanPage.Ketone to Triple(75f, 5f, 20f),
        PlanPage.Mediterra to Triple(40f, 45f, 15f),
    )

    fun getGoals(plan: PlanPage): Triple<Float, Float, Float>? {
        return goalsMap[plan]
    }
}

enum class DateRange(@StringRes val title: Int){
    AWeek(title = R.string.aweek),
    HalfMonth(title = R.string.halfmonth),
    AMonth(title = R.string.amonth),
    ThreeMonth(title = R.string.threemonth),
    SixMonth(title = R.string.sixmonth);

}

