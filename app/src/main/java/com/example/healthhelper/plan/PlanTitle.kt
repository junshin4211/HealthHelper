package com.example.healthhelper.plan

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.platform.LocalContext
import com.example.healthhelper.R

enum class PlanPage(@StringRes val title: Int) {
    DietPlan(title = R.string.plan),
    MyPlan(title = R.string.myplan),
    CompletedPlan(title = R.string.completedplan),
    EditPlan(title = R.string.editplan),
    CheckEdit(title = R.string.checkedit);

    fun getPlanTitle(context: Context):String{
        return context.getString(title)
    }
}

enum class Bar(@StringRes val title: Int){
    HighProtein(title = R.string.highprotein),
    LowCarb(title = R.string.lowcarb),
    Ketone(title = R.string.ketone),
    Mediterra(title = R.string.mediterra),
    Custom(title = R.string.custom);

    fun getbar(context: Context):String{
        return context.getString(title)
    }
}

enum class DateRange(@StringRes val title: Int){
    AWeek(title = R.string.aweek),
    HalfMonth(title = R.string.halfmonth),
    AMonth(title = R.string.amonth),
    ThreeMonth(title = R.string.threemonth),
    SixMonth(title = R.string.sixmonth);

}

