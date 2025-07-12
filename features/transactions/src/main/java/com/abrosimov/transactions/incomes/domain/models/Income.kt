package com.abrosimov.transactions.incomes.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class Income(
    val id: Int,
    val amount: String,
    val source: String,
    val currency: String,
    val subtitle: String? = null,
    val date: String,
) {

}
