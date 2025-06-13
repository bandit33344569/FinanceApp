package com.abrosimov.financeapp.data.models

import com.abrosimov.financeapp.domain.models.Category

data class TransactionResponse(
    val id: Int,
    val account: AccountBrief,
    val category: Category,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
)