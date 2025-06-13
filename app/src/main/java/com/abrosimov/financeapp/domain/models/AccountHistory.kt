package com.abrosimov.financeapp.domain.models

data class AccountHistory(
    val id: Int,
    val accountId: Int,
    val changeType: ChangeType,
    val previousState: AccountState? = null,
    val newState: AccountState,
    val changeTimestamp: String,
    val createdAt: String
)

enum class ChangeType{
    MODIFICATION,
    CREATION
}


