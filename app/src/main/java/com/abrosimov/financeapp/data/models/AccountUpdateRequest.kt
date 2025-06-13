package com.abrosimov.financeapp.data.models

data class AccountUpdateRequest(
    val name: String,
    val balance: String,
    val currency: String,
)
