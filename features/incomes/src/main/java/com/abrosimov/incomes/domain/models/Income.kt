package com.abrosimov.incomes.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class Income(
    val id: String,
    val amount: String,
    val source: String,
    val currency: String,
    val subtitle: String? = null,
    val createdAt: String,
) {

}
