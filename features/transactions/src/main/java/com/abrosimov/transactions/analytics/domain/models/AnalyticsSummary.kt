package com.abrosimov.transactions.analytics.domain.models

import java.math.BigDecimal

sealed class AnalyticsSummary {
    abstract val totalAmount: BigDecimal
    abstract val items: List<CategoryAnalyticsItem>
}

data class IncomeAnalyticsSummary(
    override val totalAmount: BigDecimal,
    override val items: List<CategoryAnalyticsItem>
) : AnalyticsSummary()

data class ExpenseAnalyticsSummary(
    override val totalAmount: BigDecimal,
    override val items: List<CategoryAnalyticsItem>
) : AnalyticsSummary()