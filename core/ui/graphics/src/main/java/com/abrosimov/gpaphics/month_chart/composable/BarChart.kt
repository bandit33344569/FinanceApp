package com.abrosimov.gpaphics.month_chart.composable

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abrosimov.gpaphics.month_chart.models.BarData
import com.abrosimov.gpaphics.month_chart.models.BarType
import com.abrosimov.gpaphics.month_chart.models.ChartData
import kotlin.math.abs

@Composable
fun BarChart(
    chartData: ChartData,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    val incomeColor = MaterialTheme.colorScheme.primary
    val expenseColor = MaterialTheme.colorScheme.error
    val zeroColor = Color.Transparent
    val labelColor = MaterialTheme.colorScheme.onBackground

    val canvasHeightPx = with(density) { 200.dp.toPx() }
    val labelOffsetPx = with(density) { 16.dp.toPx() }
    val textOffsetPx = with(density) { 16.dp.toPx() }
    val cornerRadiusPx = with(density) { 8.dp.toPx() }

    val maxValue = chartData.bars.maxOfOrNull { abs(it.value) } ?: 1f
    val scaleY = (canvasHeightPx - labelOffsetPx) / maxValue

    var animationTrigger by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        animationTrigger = true
    }

    val animatedBarHeights = chartData.bars.mapIndexed { index, barData ->
        val targetBarHeight = abs(barData.value) * scaleY
        animateFloatAsState(
            targetValue = if (animationTrigger && barData.type != BarType.Zero) targetBarHeight else 0f,
            animationSpec = tween(
                durationMillis = 500,
                delayMillis = index * 25
            ),
            label = "barHeightAnimation_$index"
        )
    }


    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val barWidth = size.width / (chartData.bars.size * 1.5f)

        chartData.bars.forEachIndexed { index, barData ->
            val x = index * barWidth * 1.5f + barWidth * 0.25f
            val animatedBarHeight by animatedBarHeights[index]

            if (barData.type != BarType.Zero && animatedBarHeight > 0f) {
                drawRoundRect(
                    color = when (barData.type) {
                        BarType.Income -> incomeColor
                        BarType.Expense -> expenseColor
                        BarType.Zero -> zeroColor
                    },
                    topLeft = Offset(x, size.height - animatedBarHeight - labelOffsetPx),
                    size = Size(barWidth, animatedBarHeight),
                    cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                )
            }

            if (index % chartData.labelFrequency == 0) {
                val textLayoutResult: TextLayoutResult = textMeasurer.measure(
                    text = barData.date,
                    style = androidx.compose.ui.text.TextStyle(
                        color = labelColor,
                        fontSize = 12.sp
                    )
                )
                val textWidth = textLayoutResult.size.width.toFloat()
                val textX = when {
                    index >= chartData.bars.size - chartData.labelFrequency -> (x + barWidth / 2 - textWidth)
                    index == 0 -> (x + barWidth / 2).coerceAtLeast(0f)
                    else -> x + barWidth / 2 - textWidth / 2
                }.coerceIn(0f, size.width - textWidth)

                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(textX, size.height - textOffsetPx)
                )
            }
        }
    }
}

// Моковые данные для превью
private fun createMockChartData(): ChartData {
    return ChartData(
        bars = listOf(
            BarData(date = "01.07", value = 1000f, type = BarType.Income),
            BarData(date = "02.07", value = -500f, type = BarType.Expense),
            BarData(date = "03.07", value = 0f, type = BarType.Zero),
            BarData(date = "04.07", value = 2000f, type = BarType.Income),
            BarData(date = "05.07", value = -1500f, type = BarType.Expense),
            BarData(date = "06.07", value = 0f, type = BarType.Zero),
            BarData(date = "07.07", value = 800f, type = BarType.Income),
            BarData(date = "08.07", value = -200f, type = BarType.Expense),
            BarData(date = "09.07", value = 0f, type = BarType.Zero),
            BarData(date = "10.07", value = 3000f, type = BarType.Income),
            BarData(date = "11.07", value = -700f, type = BarType.Expense),
            BarData(date = "12.07", value = 0f, type = BarType.Zero),
            BarData(date = "13.07", value = 1500f, type = BarType.Income),
            BarData(date = "14.07", value = -1000f, type = BarType.Expense),
            BarData(date = "15.07", value = 0f, type = BarType.Zero),
            BarData(date = "16.07", value = 1200f, type = BarType.Income),
            BarData(date = "17.07", value = -300f, type = BarType.Expense),
            BarData(date = "18.07", value = 0f, type = BarType.Zero),
            BarData(date = "19.07", value = 2500f, type = BarType.Income),
            BarData(date = "20.07", value = -800f, type = BarType.Expense),
            BarData(date = "21.07", value = 0f, type = BarType.Zero),
            BarData(date = "22.07", value = 600f, type = BarType.Income),
            BarData(date = "23.07", value = -400f, type = BarType.Expense),
            BarData(date = "24.07", value = 0f, type = BarType.Zero),
            BarData(date = "25.07", value = 1800f, type = BarType.Income),
            BarData(date = "26.07", value = -600f, type = BarType.Expense),
            BarData(date = "27.07", value = 0f, type = BarType.Zero),
            BarData(date = "28.07", value = 900f, type = BarType.Income),
            BarData(date = "29.07", value = -200f, type = BarType.Expense),
            BarData(date = "30.07", value = 0f, type = BarType.Zero),
            BarData(date = "31.07", value = 2200f, type = BarType.Income)
        ),
        labelFrequency = 10
    )
}

@Preview(showBackground = true)
@Composable
fun BarChartPreview() {
    MaterialTheme {
        BarChart(
            chartData = createMockChartData(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}