package com.example.healthhelper.dietary.test

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalTime

@Composable
fun MealsTimeRangeCategoryRepositoryTestUi(){

    val TAG = "tag_MealsTimeRangeCategoryRepositoryTestUi"

    Log.d(TAG,"MealsTimeRangeCategoryRepositoryTestUi function was called.")

    val cases = mutableListOf<LocalTime>()
    for(hour in 0..<24 step 2){
        for(minute in 0..<60 step 20){
            cases.add(LocalTime.of(hour,minute))
        }
    }

    Log.d(TAG,"cases:$cases.")

    val results = MealsTimeRangeCategoryRepositoryTest.test(cases.toList())

    Log.d(TAG,"results:$results.")

    val zipped = cases.zip(results)

    Log.d(TAG,"zipped:$zipped.")

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        zipped.forEachIndexed{ index,pair->
            val message = "The ${index}th data,${pair.first}:${pair.second}"

            Log.d(TAG,"message:$message.")

            Text(
                text = message,
                color = Color.Blue,
            )
            Spacer(modifier = Modifier.height(10.dp).fillMaxWidth())
        }
    }
}