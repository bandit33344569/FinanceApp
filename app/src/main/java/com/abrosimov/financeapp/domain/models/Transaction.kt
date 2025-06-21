package com.abrosimov.financeapp.domain.models

import com.abrosimov.financeapp.ui.models.Income

data class Transaction(
    val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String,
)
