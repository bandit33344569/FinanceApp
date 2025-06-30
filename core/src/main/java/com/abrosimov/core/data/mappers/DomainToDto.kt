package com.abrosimov.core.data.mappers

import com.abrosimov.core.data.models.dto.AccountBriefDto
import com.abrosimov.core.data.models.dto.AccountDto
import com.abrosimov.core.data.models.dto.AccountHistoryDto
import com.abrosimov.core.data.models.dto.AccountStateDto
import com.abrosimov.core.data.models.dto.CategoryDto
import com.abrosimov.core.data.models.dto.StatItemDto
import com.abrosimov.core.data.models.dto.TransactionDto
import com.abrosimov.core.domain.models.Account
import com.abrosimov.core.domain.models.AccountBrief
import com.abrosimov.core.domain.models.AccountHistory
import com.abrosimov.core.domain.models.AccountState
import com.abrosimov.core.domain.models.Category
import com.abrosimov.core.domain.models.StatItem
import com.abrosimov.core.domain.models.Transaction

/**
 * Маппер, преобразующий доменные модели в DTO для последующей передачи/хранения.
 *
 * Содержит методы для конвертации:
 * - [Account] → [AccountDto]
 * - [AccountBrief] → [AccountBriefDto]
 * - [AccountHistory] → [AccountHistoryDto]
 * - [AccountState] → [AccountStateDto]
 * - [Category] → [CategoryDto]
 * - [StatItem] → [StatItemDto]
 * - [Transaction] → [TransactionDto]
 */

fun Account.toDto(): AccountDto {
    return AccountDto(
        id = id,
        userId = userId,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun AccountBrief.toDto(): AccountBriefDto {
    return AccountBriefDto(
        id = id,
        name = name,
        balance = balance,
        currency = currency
    )
}

fun AccountHistory.toDto(): AccountHistoryDto {
    return AccountHistoryDto(
        id = id,
        accountId = accountId,
        changeType = changeType,
        previousState = previousState,
        newState = newState,
        changeTimestamp = changeTimestamp,
        createdAt = createdAt
    )
}

fun AccountState.toDto(): AccountStateDto {
    return AccountStateDto(
        id = id,
        name = name,
        balance = balance,
        currency = currency
    )
}

fun Category.toDto(): CategoryDto {
    return CategoryDto(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}

fun StatItem.toDto(): StatItemDto {
    return StatItemDto(
        categoryId = categoryId,
        categoryName = categoryName,
        emoji = emoji,
        amount = amount
    )
}

fun Transaction.toDto(): TransactionDto {
    return TransactionDto(
        id = id,
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}