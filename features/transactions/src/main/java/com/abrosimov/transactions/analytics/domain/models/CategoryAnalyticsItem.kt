package com.abrosimov.transactions.analytics.domain.models

import java.math.BigDecimal

data class CategoryAnalyticsItem(
    val categoryId: Int,
    val categoryName: String,
    val categoryEmoji: String,
    val totalAmount: BigDecimal,
    val percentage: Double,
    val isIncome: Boolean
) {

}