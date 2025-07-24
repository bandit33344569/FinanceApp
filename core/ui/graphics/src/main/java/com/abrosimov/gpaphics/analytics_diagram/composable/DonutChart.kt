package com.abrosimov.gpaphics.analytics_diagram.composable

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abrosimov.gpaphics.analytics_diagram.color_generator.ColorGenerator
import com.abrosimov.gpaphics.analytics_diagram.models.AnalyticsChartData
import com.abrosimov.gpaphics.analytics_diagram.models.AnalyticsChartItem

@Composable
fun DonutChart(
    chartData: AnalyticsChartData?,
    modifier: Modifier = Modifier,
    ringThickness: Float = 40f,
    baseTextSize: Float = 10f
) {
    var animationTrigger by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        animationTrigger = true
    }

    val data = chartData ?: return
    val animatedSweeps = data.items.mapIndexed { index, item ->
        val targetSweep = ((item.percentage / 100f) * 360f).toFloat()
        animateFloatAsState(
            targetValue = if (animationTrigger) targetSweep else 0f,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = index * 50,
                easing = FastOutSlowInEasing
            ),
            label = "sweepAngleAnimation_$index"
        )
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(200.dp)) {
            var startAngle = -90f

            data.items.forEachIndexed { index, item ->
                val sweepAngle by animatedSweeps[index]
                drawArc(
                    color = item.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(
                        size.width / 2 - size.minDimension / 2 + ringThickness / 2,
                        size.height / 2 - size.minDimension / 2 + ringThickness / 2
                    ),
                    size = Size(size.minDimension - ringThickness, size.minDimension - ringThickness),
                    style = Stroke(width = ringThickness)
                )
                startAngle += sweepAngle
            }
        }

        CategoryList(
            items = data.items,
            baseTextSize = baseTextSize,
            modifier = Modifier.padding(16.dp)
        )
    }
}



private val mockChartData = AnalyticsChartData(
    items = listOf(
        AnalyticsChartItem(
            name = "Продукты",
            percentage = 30.0,
            color = Color(0xFFF44336)
        ),
        AnalyticsChartItem(
            name = "Транспорт",
            percentage = 20.0,
            color = Color(0xFF2196F3)
        ),
        AnalyticsChartItem(
            name = "Развлечения22222",
            percentage = 25.0,
            color = Color(0xFF4CAF50)
        ),
        AnalyticsChartItem(
            name = "Счета",
            percentage = 15.0,
            color = Color(0xFFFFEB3B)
        ),
        AnalyticsChartItem(
            name = "Прочее",
            percentage = 10.0,
            color = Color(0xFF9C27B0)
        )
    )
)

@Preview(showBackground = true)
@Composable
fun DonutChartPreview() {
    DonutChart(
        chartData = mockChartData,
    )
}

private val mockLargeChartData = AnalyticsChartData(
    items = List(12) { index ->
        AnalyticsChartItem(
            name = "Развлечения22222$index",
            percentage = 100.0 / 12,
            color = ColorGenerator.getRandomColor()
        )
    }
)

@Preview(showBackground = true)
@Composable
fun DonutChartLargeDataPreview() {
    DonutChart(
        chartData = mockLargeChartData,
    )
}