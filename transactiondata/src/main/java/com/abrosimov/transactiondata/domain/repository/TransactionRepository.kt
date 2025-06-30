package com.abrosimov.transactiondata.domain.repository

import com.abrosimov.core.domain.Resource
import com.abrosimov.core.domain.models.SpecTransaction

interface TransactionRepository {
    suspend fun getTransactionFromPeriod(
        accountId:Int,
        startDate: String? = null,
        endDate: String? = null,
    ): Resource<List<SpecTransaction>>
}