package com.abrosimov.core.data.models.requests

data class AccountCreateRequest(
    val name: String,
    val balance:String,
    val currency:String,
)