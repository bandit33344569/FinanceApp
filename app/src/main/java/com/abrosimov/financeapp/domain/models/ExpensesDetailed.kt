package com.abrosimov.financeapp.domain.models

data class ExpensesDetailed(
    val id: String,
    val createdAt: String,
    val description: String,
    val sum: String,
    val dateText: String,
    val createdAtDetailed: String,
    val checkId: String,
    val expensesType: ExpensesType,
)