package com.example.healthhelper.dietary.components.piechart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthhelper.R
import com.example.healthhelper.dietary.dataclasses.vo.NutritionInfoVO
import com.example.healthhelper.plan.ui.CreatePieChart
import com.himanshoe.charty.common.toChartDataCollection
import com.himanshoe.charty.pie.model.PieData
import kotlin.math.round

@Composable
fun NutritionPieChart(
    nutritionInfoVO: NutritionInfoVO,
) {
    val texts = listOf(
        nutritionInfoVO.carbon.name,
        nutritionInfoVO.protein.name,
        nutritionInfoVO.fat.name,
    )
    val carbpercent = nutritionInfoVO.carbon.value.toFloat()
    val proteinpercent = nutritionInfoVO.protein.value.toFloat()
    val fatpercent = nutritionInfoVO.fat.value.toFloat()

    val data = listOf(
        PieData(carbpercent, carbpercent, colorResource(R.color.light_red)),
        PieData(proteinpercent, proteinpercent, colorResource(R.color.sky_blue)),
        PieData(fatpercent, fatpercent, colorResource(R.color.light_green)),
    )
    CreatePieChart(
        dataCollection = data.toChartDataCollection(),
        modifier = Modifier.size(100.dp, 100.dp)
    )

    LazyColumn(
        modifier = Modifier.height(100.dp)
    ) {
        items(texts.size) { index ->
            val entry = data[index]
            val text = texts[index]
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${text}:${round(entry.yValue * 100 / data.size)}%",
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Visible,
                )
                Canvas(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp, 0.dp, 0.dp, 0.dp),
                ) {
                    drawCircle(
                        color = entry.color,
                        radius = 20.toFloat(),
                    )
                }
            }
        }
    }
}