package com.example.healthhelper.plan.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import com.example.healthhelper.R
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.common.config.ChartDefaults
import com.himanshoe.charty.common.config.ChartyLabelTextConfig
import com.himanshoe.charty.common.config.StartAngle
import com.himanshoe.charty.pie.model.PieData
import java.util.Locale

data class PieChartConfig(
    val donut: Boolean = true,
    val showLabel: Boolean = true,
    val startAngle: StartAngle = StartAngle.Zero,
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreatePieChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    textLabelTextConfig: ChartyLabelTextConfig = ChartDefaults.defaultTextLabelConfig(),
    pieChartConfig: PieChartConfig = PieChartConfig(),
) {
    val pieChartData = dataCollection.data.fastMap { it.yValue }
    val total = pieChartData.sum()

    var startAngle = pieChartConfig.startAngle.angle

    Column(modifier) {
        Canvas(
            modifier = Modifier
                .aspectRatio(1F)
        ) {
            val radius = size.minDimension / 3
            val centerX = size.width / 2
            val centerY = size.height / 2
            val holeRadius =
                if (pieChartConfig.donut) radius * 0.4f else 0f // Adjust the hole radius based on donut config

            dataCollection.data.fastForEach { value ->
                if (value is PieData) {
                    val sweepAngle = (value.yValue / total) * 360
                    if (pieChartConfig.donut.not()) {
                        drawArc(
                            color = value.color,
                            startAngle = startAngle,
                            sweepAngle = sweepAngle,
                            useCenter = true,
                        )
                    } else {
                        drawArc(
                            color = value.color,
                            startAngle = startAngle,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            topLeft = Offset(centerX - radius, centerY - radius),
                            size = Size(radius * 2, radius * 2),
                            style = Stroke(width = radius - holeRadius)
                        )
                    }
                    startAngle += sweepAngle
                }
            }
        }
        if (pieChartConfig.showLabel) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                horizontalArrangement = Arrangement.Center,
                maxItemsInEachRow = 4,
                overflow = FlowRowOverflow.Visible
            ) {
                dataCollection.data.fastMap {
                    if (it is PieData) {
                        Box(
                            Modifier
                                .size(textLabelTextConfig.indicatorSize)
                                .clip(CircleShape)
                                .align(Alignment.CenterVertically)
                                .background(it.color)
                        )
                        Text(
                            text = "${String.format(Locale.US, "%.1f", it.xValue)}%",
                            fontSize = textLabelTextConfig.textSize,
                            fontStyle = textLabelTextConfig.fontStyle,
                            fontWeight = FontWeight(600),
                            fontFamily = textLabelTextConfig.fontFamily,
                            maxLines = textLabelTextConfig.maxLine,
                            overflow = textLabelTextConfig.overflow,
                            modifier = Modifier.padding(
                                end = 15.dp,
                                start = 4.dp
                            ),
                            color = colorResource(R.color.black_300)
                        )
                    }
                }
            }
        }
    }
}
