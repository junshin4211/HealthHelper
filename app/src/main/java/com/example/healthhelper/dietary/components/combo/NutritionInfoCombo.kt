package com.example.healthhelper.dietary.components.combo

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.healthhelper.R
import com.example.healthhelper.dietary.components.piechart.NutritionPieChart
import com.example.healthhelper.dietary.components.text.NutritionInfoText
import com.example.healthhelper.dietary.dataclasses.vo.NutritionInfoVO
import com.example.healthhelper.dietary.repository.NutritionInfoRepository

@Composable
fun NutritionInfoCombo(
    modifier: Modifier = Modifier,
    nutritionInfoVO: NutritionInfoVO,
    title: @Composable () -> Unit,
    showTitle: Boolean,
) {
    val TAG = "tag_NutritionInfoCombo"
    val isValidInfo = NutritionInfoRepository.isValidNutritionInfo(nutritionInfoVO)
    Log.e(TAG, "In NutritionInfoCombo function, isValidInfo:${isValidInfo}")
    if (isValidInfo) {
        LazyRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
        ) {
            item {
                NutritionInfoText(
                    nutritionInfoVO = nutritionInfoVO,
                    title = title,
                    showTitle = showTitle,
                )
            }
            item {
                Spacer(modifier = Modifier.width(20.dp))
            }
            item {
                LazyColumn(
                    modifier = Modifier.size(190.dp, 200.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        NutritionPieChart(nutritionInfoVO)
                    }
                }
            }
        }
    } else {
        LazyRow(
            modifier = Modifier.size(200.dp, 100.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            item {
                Text(stringResource(R.string.has_no_nutrition_info_message))
            }
        }
    }
}