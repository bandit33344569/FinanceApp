package com.abrosimov.api.models.dto

data class AccountStateDto(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
) {
}