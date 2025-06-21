package com.abrosimov.financeapp.data.mapper

import com.abrosimov.financeapp.data.models.AccountBriefDto
import com.abrosimov.financeapp.data.models.AccountDto
import com.abrosimov.financeapp.data.models.AccountHistoryDto
import com.abrosimov.financeapp.data.models.AccountStateDto
import com.abrosimov.financeapp.data.models.CategoryDto
import com.abrosimov.financeapp.data.models.Response.TransactionResponse
import com.abrosimov.financeapp.data.models.StatItemDto
import com.abrosimov.financeapp.data.models.TransactionDto
import com.abrosimov.financeapp.domain.models.Account
import com.abrosimov.financeapp.domain.models.AccountBrief
import com.abrosimov.financeapp.domain.models.AccountHistory
import com.abrosimov.financeapp.domain.models.AccountState
import com.abrosimov.financeapp.domain.models.Category
import com.abrosimov.financeapp.domain.models.SpecTransaction
import com.abrosimov.financeapp.domain.models.StatItem
import com.abrosimov.financeapp.domain.models.Transaction

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
fun AccountBriefDto.toDomain(): AccountBrief{
    return AccountBrief(
        id = id,
        name = name,
        balance = balance,
        currency = currency
    )
}

fun AccountHistoryDto.toDomain(): AccountHistory{
    return AccountHistory(
        id = id,
        accountId = accountId,
        changeType = changeType,
        previousState = previousState,
        newState = newState,
        changeTimestamp = changeTimestamp,
        createdAt = createdAt
    )
}

fun AccountStateDto.toDomain(): AccountState{
    return AccountState(
        id = id,
        name = name,
        balance = balance,
        currency = currency
    )
}

fun CategoryDto.toDomain(): Category{
    return Category(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}

fun StatItemDto.toDomain(): StatItem{
    return StatItem(
        categoryId = categoryId,
        categoryName = categoryName,
        emoji = emoji,
        amount = amount
    )
}

fun TransactionDto.toDomain(): Transaction{
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

fun TransactionResponse.toDomain(): SpecTransaction{
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