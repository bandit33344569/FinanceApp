package com.abrosimov.api.models.responses

import com.abrosimov.api.models.dto.AccountHistoryDto

data class AccountHistoryResponse(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: List<AccountHistoryDto>
)