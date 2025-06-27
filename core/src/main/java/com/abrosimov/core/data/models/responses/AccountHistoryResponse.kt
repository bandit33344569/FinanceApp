package com.abrosimov.core.data.models.responses

import com.abrosimov.core.data.models.dto.AccountHistoryDto

data class AccountHistoryResponse(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: List<AccountHistoryDto>
)