package com.abrosimov.core.domain.models

data class AccountBrief(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)