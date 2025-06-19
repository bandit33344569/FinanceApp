package com.abrosimov.financeapp.ui.models

import androidx.compose.runtime.Immutable

@Immutable
data class Expense(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val createdAt: String,
    val trailTag: String,
    val amount: String,
    val iconTag: String,
)