package com.abrosimov.api.models.requests

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class TransactionRequest(
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    @Required
    val comment: String? = null
)