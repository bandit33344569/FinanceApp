package com.abrosimov.api.models.dto

data class AccountBriefDto(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)
