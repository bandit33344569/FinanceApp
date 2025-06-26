package com.abrosimov.financeapp.domain.models

data class AccountState(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)
