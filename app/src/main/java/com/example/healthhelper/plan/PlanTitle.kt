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

object CateGoryId{
    private val cateMap = mapOf(
        PlanPage.HighProtein to 1,
        PlanPage.LowCarb to 2,
        PlanPage.Ketone to 3,
        PlanPage.Mediterra to 4,
        PlanPage.Custom to 5,
    )

    fun getCateId(plan: PlanPage):Int?{
        return cateMap[plan]
    }
}

enum class DateRange(@StringRes val title: Int){
    AWeek(title = R.string.aweek),
    HalfMonth(title = R.string.halfmonth),
    AMonth(title = R.string.amonth),
    ThreeMonth(title = R.string.threemonth),
    SixMonth(title = R.string.sixmonth);

}

enum class NutritionType(@StringRes val title: Int){
    Carb(title = R.string.carb),
    Protein(title = R.string.protein),
    Fat(title = R.string.fat),
    Fiber(title = R.string.fiber),
    Sugar(title = R.string.sugar),
    Sodium(title = R.string.sodium);

    fun getNutritionTitle(context: Context):String{
        return context.getString(title)
    }
}

