package com.abrosimov.core.domain.models

data class Category(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
)