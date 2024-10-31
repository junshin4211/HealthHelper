package com.example.healthhelper.person.widget

import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import com.example.healthhelper.person.model.WeightData
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.common.ChartSurface
import com.himanshoe.charty.common.config.AxisConfig
import com.himanshoe.charty.common.maxYValue
import com.himanshoe.charty.common.minYValue
import com.himanshoe.charty.line.config.LineChartDefaults
import com.himanshoe.charty.line.config.LineConfig
import com.himanshoe.charty.line.model.LineData

@Composable
fun LineChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    padding: Dp = 12.dp,
    axisConfig: AxisConfig = axisConfigDefaults(),
    radiusScale: Float = 0.01f,
    lineConfig: LineConfig = LineChartDefaults.defaultConfig(),
) {
    val points = dataCollection.data

    var chartWidth by remember { mutableStateOf(0F) }
    var chartHeight by remember { mutableStateOf(0F) }
    var pointBound by remember { mutableStateOf(0F) }

    val horizontalScale = chartWidth.div(points.count())
    val verticalScale = chartHeight.div((dataCollection.maxYValue() - dataCollection.minYValue()))

    // 設定顏色
    val lineColor = Brush.linearGradient(listOf(Color(0xFFFFCC80), Color(0xFFFFE0B2)))
    val backgroundColor = Brush.linearGradient(listOf(Color(0xFFFFF3E0), Color(0xFFFFF8E1)))
    val dotColor = Brush.linearGradient(listOf(Color(0xFFFFA726), Color(0xFFFFA726)))

    ChartSurface(
        padding = padding,
        chartData = dataCollection,
        modifier = modifier.drawBehind {},
        axisConfig = axisConfig
    ) {
        val minYValue = dataCollection.minYValue()

        Canvas(
            modifier = Modifier
                .background(backgroundColor)
                .fillMaxSize()
                .onSizeChanged { size ->
                    chartWidth = size.width.toFloat()
                    chartHeight = size.height.toFloat()
                    pointBound = size.width.div(points.count().times(1.2F))
                }
                .drawBehind {
                        val minValue = dataCollection.minYValue()
                        val maxValue = dataCollection.maxYValue()
                        drawYAxis(
                            color = axisConfig.axisColor,
                            stroke = axisConfig.axisStroke,
                            minValue = minValue,
                            maxValue = maxValue,
                            verticalScale = verticalScale
                        )

                        drawYAxisLabels(
                            values = points.map { it.yValue },
                            spacing = padding.toPx(),
                            textColor = axisConfig.axisColor
                        )

                }
        ) {
            val graphPathPoints = mutableListOf<PointF>()
            val radius = size.width * radiusScale

            Path().apply {
                val firstData = points.first()
                val initialX = 0f
                val initialY = size.height - ((firstData.yValue - minYValue) * verticalScale)
                moveTo(initialX, initialY)

                points.fastForEachIndexed { index, data ->
                    val centerOffset = chartDataToOffset(
                        index,
                        pointBound,
                        size,
                        data.yValue,
                        horizontalScale,
                    )
                    val x = centerOffset.x
                    val y = size.height - ((data.yValue - minYValue) * verticalScale)
                    val innerX = x.coerceIn(centerOffset.x - radius / 2, centerOffset.x + radius / 2)
                    val innerY = y.coerceIn(radius, size.height - radius)

                    graphPathPoints.add(PointF(innerX, innerY))

                    if (points.size > 1) {
                        when (index) {
                            0 -> moveTo(x, y)
                            else -> lineTo(innerX, innerY)
                        }
                    }

                    if (points.count() < 14) {
                        drawXAxisLabels(
                            data = data.xValue,
                            center = centerOffset,
                            count = points.count(),
                            padding = padding.toPx(),
                            minLabelCount = axisConfig.minLabelCount,
                        )
                    }
                }

                val pathEffect = if (lineConfig.hasSmoothCurve) PathEffect.cornerPathEffect(radius) else null
                drawPath(
                    path = this,
                    brush = lineColor,
                    style = Stroke(width = lineConfig.strokeSize, pathEffect = pathEffect),
                )
            }
            if (lineConfig.hasDotMarker) {
                graphPathPoints.fastForEach { point ->
                    drawCircle(
                        brush = dotColor,
                        radius = radiusScale * size.width,
                        center = Offset(point.x, point.y)
                    )
                }
            }
        }
    }
}





fun generateMockWeightDataList(): List<WeightData> {
    return listOf(
        WeightData(2, 170.0, 70.0, 14.5, "2023-02-01", 23.5),
        WeightData(3, 170.0, 65.0, 14.0, "2023-03-01", 22.5),
        WeightData(4, 170.0, 67.0, 14.8, "2023-04-01", 23.1),
        WeightData(5, 170.0, 66.0, 14.6, "2023-05-01", 22.9),
        WeightData(6, 170.0, 64.0, 13.8, "2023-06-01", 22.3),
        WeightData(7, 170.0, 63.0, 13.5, "2023-07-01", 22.0),
        WeightData(8, 170.0, 62.0, 13.2, "2023-08-01", 21.7),
        WeightData(9, 170.0, 55.0, 12.9, "2023-09-01", 21.5),
    )
}

internal fun chartDataToOffset(
    index: Int,
    bound: Float,
    size: Size,
    data: Float,
    scaleFactor: Float
): Offset {
    val startX = index * bound * 1.2F
    val endX = (index + 1) * bound * 1.2F
    val y = size.height - data * scaleFactor
    return Offset((startX + endX) / 2F, y)
}

internal fun DrawScope.drawXAxisLabels(
    data: Any,
    center: Offset,
    count: Int,
    padding: Float,
    minLabelCount: Int,
    textColor: Color = Color.Black,
) {
    val divisibleFactor = if (count > 10) count else 1
    val textSizeFactor = if (count > 10) 3 else 30

    val textBounds = Rect()
    val textPaint = Paint().apply {
        color = textColor.toArgb()
        textSize = size.width / textSizeFactor / divisibleFactor
        textAlign = Paint.Align.CENTER
        getTextBounds(data.toString(), 0, data.toString().length, textBounds)
    }

    drawContext.canvas.nativeCanvas.drawText(
        data.toString().take(minLabelCount),
        center.x,
        size.height + padding,
        textPaint
    )
}

fun DrawScope.drawYAxis(
    color: Color,
    stroke: Float,
    minValue: Float,
    maxValue: Float,
    verticalScale: Float
) {
    val minYPosition = size.height - ((minValue - minValue) * verticalScale)
    val maxYPosition = size.height - ((maxValue - minValue) * verticalScale)

    drawLine(
        start = Offset(0f, minYPosition),
        end = Offset(0f, maxYPosition),
        color = color,
        strokeWidth = stroke
    )

    drawLine(
        start = Offset(0f, minYPosition),
        end = Offset(size.width, minYPosition),
        color = color,
        strokeWidth = stroke
    )
}

fun axisConfigDefaults() = AxisConfig(
    showGridLabel = false,
    showAxes = false,
    showGridLines = false,
    axisStroke = 2F,
    axisColor = Color.Black,
    minLabelCount = 2
)

fun DrawScope.drawYAxisLabels(
    values: List<Float>,
    spacing: Float,
    textColor: Color = Color.Black,
) {
    val maxLabelCount = 4
    val maxLabelValue = values.maxOrNull() ?: return
    val minLabelValue = values.minOrNull() ?: return
    val labelRange = maxLabelValue - minLabelValue

    val textPaint = Paint().apply {
        color = textColor.toArgb()
        textSize = size.width / 30
        textAlign = Paint.Align.CENTER
    }

    val labelSpacing = size.height / (maxLabelCount - 1)

    repeat(maxLabelCount) { i ->
        val y = size.height - (i * labelSpacing)
        val x = 0F.minus(spacing)
        val labelValue = minLabelValue + ((i * labelRange) / (maxLabelCount - 1))

        val text = if (labelValue.toString().length > 4) {
            labelValue.toString().take(4)
        } else {
            labelValue.toString()
        }

        drawContext.canvas.nativeCanvas.drawText(
            text,
            x,
            y,
            textPaint
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun LineChartPreview(modifier: Modifier = Modifier) {
    Column(modifier) {
        val weightData = generateMockWeightDataList()
        val chartDataCollection = ChartDataCollection(weightData.map { LineData(it.weight.toFloat(), it.recordDate) })
        LineChart(
            dataCollection = chartDataCollection,
            modifier = Modifier
                .size(450.dp),
        )
    }
}


