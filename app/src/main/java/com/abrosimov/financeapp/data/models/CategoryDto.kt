package com.abrosimov.financeapp.data.models

data class CategoryDto(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
)