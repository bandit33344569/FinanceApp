package com.abrosimov.impl.models

import com.abrosimov.api.models.dto.AccountStateDto
import com.abrosimov.api.models.dto.ChangeType


data class AccountHistory(
    val id: Int,
    val accountId: Int,
    val changeType: ChangeType,
    val previousState: AccountStateDto? = null,
    val newState: AccountStateDto,
    val changeTimestamp: String,
    val createdAt: String
)



