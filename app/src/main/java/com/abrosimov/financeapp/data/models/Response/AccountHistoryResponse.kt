package com.abrosimov.financeapp.data.models.Response

import com.abrosimov.financeapp.data.models.AccountHistoryDto

data class AccountHistoryResponse(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: List<AccountHistoryDto>
)