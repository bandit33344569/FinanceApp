package com.abrosimov.financeapp.ui.models

data class IncomesSummary(
    val incomes: List<Income>,
    val totalAmount: Double,
    val currency: String = "₽"
)