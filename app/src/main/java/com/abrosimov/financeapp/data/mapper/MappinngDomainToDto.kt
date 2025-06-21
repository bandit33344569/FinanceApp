package com.abrosimov.financeapp.data.mapper

import com.abrosimov.financeapp.data.models.AccountBriefDto
import com.abrosimov.financeapp.data.models.AccountDto
import com.abrosimov.financeapp.data.models.AccountHistoryDto
import com.abrosimov.financeapp.data.models.AccountStateDto
import com.abrosimov.financeapp.data.models.CategoryDto
import com.abrosimov.financeapp.data.models.StatItemDto
import com.abrosimov.financeapp.data.models.TransactionDto
import com.abrosimov.financeapp.domain.models.Account
import com.abrosimov.financeapp.domain.models.AccountBrief
import com.abrosimov.financeapp.domain.models.AccountHistory
import com.abrosimov.financeapp.domain.models.AccountState
import com.abrosimov.financeapp.domain.models.Category
import com.abrosimov.financeapp.domain.models.StatItem
import com.abrosimov.financeapp.domain.models.Transaction


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