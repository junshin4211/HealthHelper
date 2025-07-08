package com.example.healthhelper.planpage.ui

import androidx.compose.ui.res.stringResource
import com.example.healthhelper.R
import com.example.healthhelper.plan.PlanPage

object CategoryID {
    private val cateMap = mapOf(
        R.string.highprotein to 1,
        R.string.lowcarb to 2,
        R.string.ketone to 3,
        R.string.mediterra to 4,
        R.string.custom to 5,
    )

    fun getCateId(cateName: Int):Int?{
        return cateMap[cateName]
    }
}