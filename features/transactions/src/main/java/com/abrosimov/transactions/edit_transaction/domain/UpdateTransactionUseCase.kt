package com.abrosimov.transactions.edit_transaction.domain

import com.abrosimov.api.models.requests.TransactionRequest
import com.abrosimov.api.repository.TransactionRepository
import com.abrosimov.impl.models.SpecTransaction
import com.abrosimov.impl.models.mappers.toDomain
import com.abrosimov.utils.models.Resource
import com.abrosimov.utils.models.map
import com.abrosimov.utils.retryWithDelay
import javax.inject.Inject

class UpdateTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(
        transactionId: Int,
        transactionRequest: TransactionRequest
    ): Resource<SpecTransaction> {
        return retryWithDelay {
            transactionRepository.updateTransaction(transactionId, transactionRequest)
                .map { it.toDomain() }
        }
    }
}