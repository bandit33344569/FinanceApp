package com.abrosimov.financeapp.data.models

import com.abrosimov.financeapp.domain.models.AccountHistory

data class AccountHistoryResponse(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: List<AccountHistory>
)
