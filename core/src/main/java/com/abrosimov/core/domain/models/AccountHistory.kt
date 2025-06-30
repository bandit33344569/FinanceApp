package com.abrosimov.core.domain.models

import com.abrosimov.core.data.models.dto.AccountStateDto
import com.abrosimov.core.data.models.dto.ChangeType

data class AccountHistory(
    val id: Int,
    val accountId: Int,
    val changeType: ChangeType,
    val previousState: AccountStateDto? = null,
    val newState: AccountStateDto,
    val changeTimestamp: String,
    val createdAt: String
)



