package com.abrosimov.core.data.models.requests

data class AccountUpdateRequest(
    val name: String,
    val balance: String,
    val currency: String,
)