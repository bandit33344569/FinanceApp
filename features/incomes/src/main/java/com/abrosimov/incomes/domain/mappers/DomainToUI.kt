package com.abrosimov.incomes.domain.mappers

import com.abrosimov.core.domain.models.SpecTransaction
import com.abrosimov.incomes.domain.models.Income

fun SpecTransaction.toIncome(): Income {
    return Income(
        id = id.toString(),
        amount = amount,
        source = category.name,
        currency = currency,
        subtitle = comment,
        createdAt = createdAt,
    )
}