package com.abrosimov.gpaphics.month_chart.models

import androidx.compose.ui.graphics.Color

data class BarData(
    val date: String,
    val value: Float,
    val type: BarType
)
