package com.abrosimov.api.repository

import com.abrosimov.api.models.dbo.TransactionEntity
import com.abrosimov.api.models.dto.SpecTransactionDto
import com.abrosimov.api.models.dto.TransactionDto
import com.abrosimov.api.models.requests.TransactionRequest
import com.abrosimov.utils.models.Resource

interface TransactionRepository {
    suspend fun getTransactionFromPeriod(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null,
    ): Resource<List<SpecTransactionDto>>

    suspend fun getTransactionById(
        transactionId: Int
    ): Resource<SpecTransactionDto>

    suspend fun updateTransaction(
        transactionId: Int,
        transactionRequest: TransactionRequest
    ): Resource<SpecTransactionDto>

    suspend fun deleteTransaction(
        transactionId: Int
    )

    suspend fun createTransaction(
        transactionRequest: TransactionRequest
    ): Resource<TransactionDto>

    suspend fun getLocalChanges(): Resource<List<TransactionEntity>>
}