package com.abrosimov.financeapp.data.models

data class AccountCreateRequest(
    val name: String,
    val balance:String,
    val currency:String,
)
