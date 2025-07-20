package com.abrosimov.api.repository

import com.abrosimov.api.models.dto.TransactionDto
import com.abrosimov.api.models.dto.requests.TransactionRequest
import com.abrosimov.api.models.dto.responses.TransactionResponse
import com.abrosimov.utils.models.Resource

interface TransactionRepository {
    suspend fun getTransactionFromPeriod(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null,
    ): Resource<List<TransactionResponse>>

    suspend fun getTransactionById(
        transactionId: Int
    ): Resource<TransactionResponse>

    suspend fun updateTransaction(
        transactionId: Int,
        transactionRequest: TransactionRequest
    ): Resource<TransactionResponse>

    suspend fun deleteTransaction(
        transactionId: Int
    )

    suspend fun createTransaction(
        transactionRequest: TransactionRequest
    ): Resource<TransactionDto>

}