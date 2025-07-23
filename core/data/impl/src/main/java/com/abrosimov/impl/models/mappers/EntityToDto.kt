package com.abrosimov.impl.models.mappers

import com.abrosimov.api.models.dbo.AccountEntity
import com.abrosimov.api.models.dbo.CategoryEntity
import com.abrosimov.api.models.dbo.TransactionEntity
import com.abrosimov.api.models.dto.AccountDto
import com.abrosimov.api.models.dto.CategoryDto
import com.abrosimov.api.models.dto.SpecTransactionDto
import com.abrosimov.api.models.dto.TransactionDto
import com.abrosimov.api.models.dto.requests.TransactionRequest
import com.abrosimov.api.models.dto.responses.TransactionResponse
import com.abrosimov.utils.dateutils.DateUtils


fun CategoryEntity.toDto(): CategoryDto {
    return CategoryDto(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}

fun AccountEntity.toDto(): AccountDto {
    return AccountDto(
        id = id,
        userId = userId,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = DateUtils.longToIsoString(createdAt),
        updatedAt = DateUtils.longToIsoString(updatedAt)
    )
}

fun TransactionEntity.toSpecTransactionDto(categoryEntity: CategoryEntity): SpecTransactionDto {
    return SpecTransactionDto(
        id = localId,
        amount = amount,
        transactionDate = DateUtils.longToIsoString(transactionDate),
        comment = comment,
        createdAt = DateUtils.longToIsoString(createdAt),
        updatedAt = DateUtils.longToIsoString(updatedAt),
        category = categoryEntity.toDto(),
    )
}

fun TransactionEntity.toTransactionDto(categoryId: Int): TransactionDto {
    return TransactionDto(
        id = localId,
        amount = amount,
        transactionDate = DateUtils.longToIsoString(transactionDate),
        comment = comment,
        createdAt = DateUtils.longToIsoString(createdAt),
        updatedAt = DateUtils.longToIsoString(updatedAt),
        categoryId = categoryId
    )
}

fun TransactionEntity.toCreateRequest(accountId: Int): TransactionRequest {
    return TransactionRequest(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = DateUtils.longToIsoString(transactionDate),
        comment = comment
    )
}


