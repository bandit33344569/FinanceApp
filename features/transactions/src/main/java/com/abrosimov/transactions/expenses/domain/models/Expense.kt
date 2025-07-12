package com.abrosimov.transactions.expenses.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class Expense(
    val id: Int,
    val title: String,
    val subtitle: String? = null,
    val data: String,
    val amount: String,
    val iconTag: String,
    val currency: String
)