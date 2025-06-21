package com.abrosimov.financeapp.data.models

import android.icu.util.Currency

/**
 *
 */
data class SpecTransactionDto(
    val id: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String,
    val category: CategoryDto,
    val currency: String,
) {
}