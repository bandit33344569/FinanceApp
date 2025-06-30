package com.abrosimov.core.data.mappers


import com.abrosimov.core.data.models.dto.AccountDto
import com.abrosimov.core.data.models.dto.CategoryDto
import com.abrosimov.core.data.models.dto.StatItemDto
import com.abrosimov.core.data.models.dto.TransactionDto
import com.abrosimov.core.data.models.responses.TransactionResponse
import com.abrosimov.core.domain.models.Account
import com.abrosimov.core.domain.models.Category
import com.abrosimov.core.domain.models.SpecTransaction
import com.abrosimov.core.domain.models.StatItem
import com.abrosimov.core.domain.models.Transaction

/**
 * Маппер, преобразующий DTO в доменные модели.
 *
 * Содержит методы для конвертации:
 * - [AccountDto] → [Account]
 * - [CategoryDto] → [Category]
 * - [StatItemDto] → [StatItem]
 * - [TransactionDto] → [Transaction]
 * - [TransactionResponse] → [SpecTransaction]
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

fun TransactionResponse.toDomain(): SpecTransaction {
    return SpecTransaction(
        id = id,
        accountId = account.id,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt,
        category = category.toDomain(),
        currency = account.currency

    )
}