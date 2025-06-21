package com.abrosimov.financeapp.data.models.Request

data class AccountUpdateRequest(
    val name: String,
    val balance: String,
    val currency: String,
)