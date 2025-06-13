package com.abrosimov.financeapp.domain.models

data class AccountBrief(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)