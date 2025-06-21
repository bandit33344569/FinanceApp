package com.abrosimov.financeapp.data.models.Response

import com.abrosimov.financeapp.data.models.AccountBriefDto
import com.abrosimov.financeapp.data.models.CategoryDto

data class TransactionResponse(
    val id: Int,
    val account: AccountBriefDto,
    val category: CategoryDto,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
)