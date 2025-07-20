package com.abrosimov.api.models.dto.requests

data class AccountUpdateRequest(
    val name: String,
    val balance: String,
    val currency: String,
)