package com.abrosimov.transactions.analytics.domain.models

import java.math.BigDecimal

data class AnalyticsSummary(
    val totalAmount: BigDecimal,
    val items: List<CategoryAnalyticsItem>
)
