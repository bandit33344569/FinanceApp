package com.abrosimov.financeapp.domain.models

data class Expenses(
    val id: String,
    val title: String,
    val subtitle: String,
    val createdAt: String,
    val trailTag: String,
    val trailText: String,
    val iconTag: String,
)