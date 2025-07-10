package com.abrosimov.api.models.requests

data class AccountUpdateRequest(
    val name: String,
    val balance: String,
    val currency: String,
)