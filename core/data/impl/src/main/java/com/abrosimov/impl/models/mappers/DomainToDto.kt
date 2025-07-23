package com.abrosimov.impl.models.mappers

import com.abrosimov.api.models.dto.AccountBriefDto
import com.abrosimov.api.models.dto.AccountDto
import com.abrosimov.api.models.dto.AccountHistoryDto
import com.abrosimov.api.models.dto.AccountStateDto
import com.abrosimov.api.models.dto.CategoryDto
import com.abrosimov.api.models.dto.StatItemDto
import com.abrosimov.api.models.dto.TransactionDto
import com.abrosimov.impl.models.Account
import com.abrosimov.impl.models.AccountBrief
import com.abrosimov.impl.models.AccountHistory
import com.abrosimov.impl.models.AccountState
import com.abrosimov.impl.models.Category
import com.abrosimov.impl.models.StatItem
import com.abrosimov.impl.models.Transaction


/**
 * Маппер, преобразующий доменные модели в DTO для последующей передачи/хранения.
 *
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
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}