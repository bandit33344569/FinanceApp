package com.abrosimov.transactions.incomes.domain.mappers

import com.abrosimov.impl.models.SpecTransaction
import com.abrosimov.transactions.incomes.domain.models.Income


fun SpecTransaction.toIncome(): Income {
    return Income(
        id = id,
        amount = amount,
        source = category.name,
        currency = currency,
        subtitle = comment,
        createdAt = createdAt,
    )
}