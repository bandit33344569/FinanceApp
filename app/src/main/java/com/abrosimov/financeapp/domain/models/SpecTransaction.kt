package com.abrosimov.financeapp.domain.models

import com.abrosimov.financeapp.data.models.CategoryDto

data class SpecTransaction(
    val id: Int,
    val accountId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String,
    val category: Category,
    val currency: String,
)