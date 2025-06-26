package com.abrosimov.financeapp.domain.models

import com.abrosimov.financeapp.data.models.AccountStateDto
import com.abrosimov.financeapp.data.models.ChangeType

data class AccountHistory(
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


