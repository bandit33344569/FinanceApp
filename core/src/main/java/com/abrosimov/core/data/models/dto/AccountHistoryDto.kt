package com.abrosimov.core.data.models.dto


data class AccountHistoryDto(
    val id: Int,
    val accountId: Int,
    val changeType: ChangeType,
    val previousState: AccountStateDto? = null,
    val newState: AccountStateDto,
    val changeTimestamp: String,
    val createdAt: String
)

enum class ChangeType{
    MODIFICATION,
    CREATION
}


