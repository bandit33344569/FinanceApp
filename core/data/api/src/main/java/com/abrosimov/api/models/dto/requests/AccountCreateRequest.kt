package com.abrosimov.api.models.dto.requests

data class AccountCreateRequest(
    val name: String,
    val balance:String,
    val currency:String,
)