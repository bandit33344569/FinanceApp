package com.abrosimov.financeapp.ui.models.mappers

import com.abrosimov.financeapp.domain.models.SpecTransaction
import com.abrosimov.financeapp.ui.models.Expense
import com.abrosimov.financeapp.ui.models.Income

fun SpecTransaction.toExpense(): Expense{
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

fun SpecTransaction.toIncome():Income{
    return Income(
        id = id.toString(),
        amount = amount,
        source = category.name,
        currency = currency,
        subtitle = comment,
        createdAt = createdAt,
    )
}

fun SpecTransaction.toHistoryIncome(){

}

fun SpecTransaction.toHistoryExpense(){

}