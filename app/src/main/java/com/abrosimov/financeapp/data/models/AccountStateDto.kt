package com.abrosimov.financeapp.data.models

data class AccountStateDto(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
) {
}