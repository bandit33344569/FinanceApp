package com.abrosimov.gpaphics.month_chart.models

data class ChartData(
    val bars: List<BarData>,
    val labelFrequency: Int = 3
)