package com.abrosimov.api.models.dto.responses

import com.abrosimov.api.models.dto.AccountBriefDto
import com.abrosimov.api.models.dto.CategoryDto


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