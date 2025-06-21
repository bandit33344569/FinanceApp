package com.abrosimov.financeapp.ui.models

data class ExpensesSummary(
    val expenses: List<Expense>,
    val totalAmount: Double,
    val currency: String = "₽"
)
