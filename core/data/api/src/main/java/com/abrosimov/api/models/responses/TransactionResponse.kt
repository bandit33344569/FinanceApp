package com.abrosimov.api.models.responses

import com.abrosimov.api.models.dbo.TransactionEntity
import com.abrosimov.api.models.dto.AccountBriefDto
import com.abrosimov.api.models.dto.CategoryDto
import com.abrosimov.api.models.dto.SpecTransactionDto
import com.abrosimov.utils.dateutils.DateUtils
import java.util.Date


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

fun TransactionResponse.toSpecTransactionDto(): SpecTransactionDto {
    return SpecTransactionDto(
        id = id,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt,
        category = category,
    )
}

fun TransactionResponse.toEntity(localId: Int): TransactionEntity {
    return TransactionEntity(
        serverId = id,
        categoryId = category.id,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt,
        localUpdatedAt = null,
        isDeleted = false,
        lastSyncedAt = DateUtils.dateToIsoString(Date()),
        localId = localId,
    )
}