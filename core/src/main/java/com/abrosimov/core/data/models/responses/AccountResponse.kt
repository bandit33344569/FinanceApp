package com.abrosimov.core.data.models.responses

import com.abrosimov.core.domain.models.StatItem

data class AccountResponse(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val incomeStats: List<StatItem>,
    val expenseStats: List<StatItem>,
    val createdAt: String,
    val updatedAt: String
)