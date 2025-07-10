package com.abrosimov.api.repository

import com.abrosimov.api.models.responses.TransactionResponse
import com.abrosimov.utils.models.Resource

interface TransactionRepository {
    suspend fun getTransactionFromPeriod(
        accountId:Int,
        startDate: String? = null,
        endDate: String? = null,
    ): Resource<List<TransactionResponse>>
}