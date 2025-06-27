package com.abrosimov.expenses.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class Expense(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val createdAt: String,
    val amount: String,
    val iconTag: String,
    val currency: String
)