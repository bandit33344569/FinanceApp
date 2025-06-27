package com.abrosimov.expenses.domain.mappers

import com.abrosimov.core.domain.models.SpecTransaction
import com.abrosimov.expenses.domain.models.Expense

fun SpecTransaction.toExpense(): Expense {
    return Expense(
        id = id.toString(),
        title = category.name,
        subtitle = comment,
        createdAt = createdAt,
        amount = amount,
        iconTag = category.emoji,
        currency = currency
    )
}