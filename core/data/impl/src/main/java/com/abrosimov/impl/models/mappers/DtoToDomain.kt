package com.abrosimov.impl.models.mappers

import com.abrosimov.api.models.dto.AccountDto
import com.abrosimov.api.models.dto.CategoryDto
import com.abrosimov.api.models.dto.SpecTransactionDto
import com.abrosimov.api.models.dto.StatItemDto
import com.abrosimov.api.models.dto.TransactionDto
import com.abrosimov.api.models.responses.TransactionResponse
import com.abrosimov.impl.models.Account
import com.abrosimov.impl.models.Category
import com.abrosimov.impl.models.SpecTransaction
import com.abrosimov.impl.models.StatItem
import com.abrosimov.impl.models.Transaction


/**
 * Маппер, преобразующий DTO в доменные модели.
 *
 * Содержит методы для конвертации:
 * - [com.abrosimov.api.models.AccountDto] → [com.abrosimov.core.domain.models.Account]
 * - [com.abrosimov.api.models.CategoryDto] → [com.abrosimov.core.domain.models.Category]
 * - [com.abrosimov.api.models.StatItemDto] → [com.abrosimov.core.domain.models.StatItem]
 * - [com.abrosimov.api.models.TransactionDto] → [com.abrosimov.core.domain.models.Transaction]
 * - [com.abrosimov.api.models.responses.TransactionResponse] → [com.abrosimov.core.domain.models.SpecTransaction]
 */

fun AccountDto.toDomain(): Account {
    return Account(
        id = id,
        userId = userId,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

fun CategoryDto.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}

fun StatItemDto.toDomain(): StatItem {
    return StatItem(
        categoryId = categoryId,
        categoryName = categoryName,
        emoji = emoji,
        amount = amount
    )
}

fun TransactionDto.toDomain(): Transaction {
    return Transaction(
        id = id,
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

fun SpecTransactionDto.toDomain(): SpecTransaction{
    return SpecTransaction(
        id = id,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt,
        category = category.toDomain(),
        )
}

fun TransactionResponse.toDomain(): SpecTransaction {
    return SpecTransaction(
        id = id,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt,
        category = category.toDomain(),

    )
}