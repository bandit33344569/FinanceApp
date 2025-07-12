package com.abrosimov.transactions.edit_transaction.domain

import com.abrosimov.api.models.requests.TransactionRequest
import com.abrosimov.api.repository.TransactionRepository
import com.abrosimov.impl.models.Transaction
import com.abrosimov.impl.models.mappers.toDomain
import com.abrosimov.utils.models.Resource
import com.abrosimov.utils.models.map
import com.abrosimov.utils.retryWithDelay
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,

){
    suspend operator fun invoke(
        transactionRequest: TransactionRequest
    ): Resource<Transaction>{
        return retryWithDelay() {
            transactionRepository.createTransaction(transactionRequest).map { it.toDomain() }
        }
    }
}