package com.abrosimov.transactions.expenses.domain.mappers

import com.abrosimov.impl.models.SpecTransaction
import com.abrosimov.transactions.expenses.domain.models.Expense


fun SpecTransaction.toExpense(): Expense {
    return Expense(
        id = id,
        title = category.name,
        subtitle = comment,
        createdAt = createdAt,
        amount = amount,
        iconTag = category.emoji,
        currency = currency
    )
}