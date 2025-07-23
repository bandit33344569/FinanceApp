package com.abrosimov.impl.models.mappers

import com.abrosimov.api.models.dbo.AccountEntity
import com.abrosimov.api.models.dbo.CategoryEntity
import com.abrosimov.api.models.dbo.TransactionEntity
import com.abrosimov.api.models.dto.AccountDto
import com.abrosimov.api.models.dto.CategoryDto
import com.abrosimov.api.models.dto.requests.AccountUpdateRequest
import com.abrosimov.api.models.dto.requests.TransactionRequest
import com.abrosimov.api.models.dto.responses.TransactionResponse
import com.abrosimov.utils.dateutils.DateUtils

fun CategoryDto.toEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}

fun AccountDto.toEntity(localUpdatedAt: String): AccountEntity {
    return AccountEntity(
        id = id,
        userId = userId,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = DateUtils.isoStringToLong(createdAt),
        updatedAt = DateUtils.isoStringToLong(updatedAt),
        localUpdatedAt = DateUtils.isoStringToLong(localUpdatedAt)
    )
}

fun AccountUpdateRequest.toEntity(currentAccountEntity: AccountEntity): AccountEntity {
    return AccountEntity(
        id = currentAccountEntity.id,
        userId = currentAccountEntity.userId,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = currentAccountEntity.createdAt,
        updatedAt = currentAccountEntity.updatedAt,
        localUpdatedAt = System.currentTimeMillis()
    )
}

fun TransactionRequest.toUpdateEntity(currentTransaction: TransactionEntity, localUpdatedAt: Long ): TransactionEntity{
    return TransactionEntity(
        localId = currentTransaction.localId,
        serverId = currentTransaction.serverId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = DateUtils.isoStringToLong(transactionDate),
        comment = comment,
        createdAt = currentTransaction.createdAt,
        updatedAt = currentTransaction.updatedAt,
        localUpdatedAt = localUpdatedAt,
        isDeleted = currentTransaction.isDeleted,
        lastSyncedAt = currentTransaction.lastSyncedAt
    )
}

fun TransactionRequest.toCreateEntity(): TransactionEntity{
    return TransactionEntity(
        localId = 0,
        serverId = null,
        categoryId = categoryId,
        amount = amount,
        transactionDate = DateUtils.isoStringToLong(transactionDate),
        comment = comment,
        createdAt = System.currentTimeMillis(),
        updatedAt = System.currentTimeMillis(),
        localUpdatedAt = System.currentTimeMillis(),
        isDeleted = false,
        lastSyncedAt = 0L
    )
}

fun TransactionResponse.toEntity(localId: Int): TransactionEntity{
    return TransactionEntity(
        localId = localId,
        serverId = id,
        categoryId = category.id,
        amount = amount,
        transactionDate = DateUtils.isoStringToLong(transactionDate),
        comment = comment,
        createdAt = DateUtils.isoStringToLong(createdAt),
        updatedAt = DateUtils.isoStringToLong(updatedAt),
        localUpdatedAt = System.currentTimeMillis(),
        isDeleted = false,
        lastSyncedAt = System.currentTimeMillis()
    )
}

