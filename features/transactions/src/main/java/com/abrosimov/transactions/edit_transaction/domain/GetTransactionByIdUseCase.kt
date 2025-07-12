package com.abrosimov.transactions.edit_transaction.domain

import com.abrosimov.api.repository.TransactionRepository
import com.abrosimov.impl.models.SpecTransaction
import com.abrosimov.impl.models.mappers.toDomain
import com.abrosimov.utils.models.Resource
import com.abrosimov.utils.models.map
import com.abrosimov.utils.retryWithDelay
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(
        transactionId: Int
    ): Resource<SpecTransaction> {
        return retryWithDelay {
            transactionRepository.getTransactionById(transactionId).map { it.toDomain() }
        }
    }
}