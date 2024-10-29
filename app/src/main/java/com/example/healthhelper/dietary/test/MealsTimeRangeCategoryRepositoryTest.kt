package com.example.healthhelper.dietary.test

import android.util.Log
import androidx.compose.runtime.Composable
import com.example.healthhelper.dietary.repository.MealsTimeRangeCategoryRepository
import java.time.LocalTime

object MealsTimeRangeCategoryRepositoryTest {

    val TAG = "tag_MealsTimeRangeCategoryRepositoryTest"

    @Composable
    fun test(cases: List<LocalTime>): List<Int> {
        Log.d(TAG,"cases:$cases")
        val results = cases.map { case ->
            MealsTimeRangeCategoryRepository.categorizeMeal(case)
        }
        Log.d(TAG,"results:$results")
        return results
    }
}