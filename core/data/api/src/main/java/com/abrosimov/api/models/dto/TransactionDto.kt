package com.abrosimov.api.models.dto

import com.abrosimov.api.models.dbo.TransactionEntity

data class TransactionDto(
    val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String,
)

fun TransactionDto.toEntity(localId: Int): TransactionEntity {
    return TransactionEntity(
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt,
        localUpdatedAt = null,
        isDeleted = false,
        lastSyncedAt = null,
        localId = localId,
        serverId = id
    )
}
