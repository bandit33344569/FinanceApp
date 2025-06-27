package com.abrosimov.core.data.models.responses

import com.abrosimov.core.data.models.dto.AccountBriefDto
import com.abrosimov.core.data.models.dto.CategoryDto

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