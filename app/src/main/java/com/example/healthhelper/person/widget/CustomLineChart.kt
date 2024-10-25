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
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.common.ChartSurface
import com.himanshoe.charty.common.config.AxisConfig
import com.himanshoe.charty.common.config.ChartDefaults
import com.himanshoe.charty.common.maxYValue
import com.himanshoe.charty.common.minYValue
import com.himanshoe.charty.common.toChartDataCollection
import com.himanshoe.charty.common.ui.drawGridLines
import com.himanshoe.charty.common.ui.drawXAxis
import com.himanshoe.charty.common.ui.drawYAxis
import com.himanshoe.charty.line.config.LineChartColors
import com.himanshoe.charty.line.config.LineChartDefaults
import com.himanshoe.charty.line.config.LineConfig
import com.himanshoe.charty.line.model.LineData

@Composable
fun LineChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    padding: Dp = 12.dp,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    radiusScale: Float = 0.01f,
    lineConfig: LineConfig = LineChartDefaults.defaultConfig(),
    chartColors: LineChartColors = LineChartDefaults.defaultColor(),
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
    val borderColor = Color(0xFFFFA726)

    ChartSurface(
        padding = padding,
        chartData = dataCollection,
        modifier = modifier.drawBehind {
//            drawRect(
//                color = borderColor,
//                size = size,
//                style = Stroke(width = 2.dp.toPx()) ,
//            )
        },
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
                    pointBound = size.width.div(
                        points
                            .count()
                            .times(1.2F)
                    )
                }
                .drawBehind {
                    if (axisConfig.showAxes) {
                        drawYAxis(axisConfig.axisColor, axisConfig.axisStroke)
                        drawXAxis(axisConfig.axisColor, axisConfig.axisStroke)
                    }
//                    if (axisConfig.showGridLines) {
//                        drawGridLines(chartWidth, chartHeight, padding.toPx())
//                    }
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
                    val innerX =
                        x.coerceIn(centerOffset.x - radius / 2, centerOffset.x + radius / 2)
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

                val pathEffect =
                    if (lineConfig.hasSmoothCurve) PathEffect.cornerPathEffect(radius) else null
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


@Composable
@Preview
private fun LineChartPreview(modifier: Modifier = Modifier) {
    Column(modifier) {
        LineChart(
            dataCollection = generateMockPointList().toChartDataCollection(),
            modifier = Modifier
                .size(450.dp),
        )
    }
}

fun generateMockPointList(): List<LineData> {
    return listOf(
        LineData(0F, "Jan"),
        LineData(10F, "Feb"),
        LineData(05F, "Mar"),
        LineData(50F, "Apr"),
        LineData(03F, "June"),
        LineData(9F, "July"),
        LineData(40F, "Aug"),
        LineData(60F, "Sept"),
        LineData(33F, "Oct"),
        LineData(11F, "Nov"),
        LineData(27F, "Dec"),
        LineData(10F, "Jan"),
        LineData(13F, "Oct"),
        LineData(0F, "Dec"),
        LineData(10F, "Jan"),
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