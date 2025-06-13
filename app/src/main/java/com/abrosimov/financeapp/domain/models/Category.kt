package com.abrosimov.financeapp.domain.models

data class Category(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
)