package com.abrosimov.financeapp.domain.usecase

import com.abrosimov.financeapp.domain.models.SpecTransaction
import com.abrosimov.financeapp.domain.repo.FinanceRepository
import com.abrosimov.financeapp.domain.repo.Resource
import com.abrosimov.financeapp.domain.repo.retryWithDelay
import jakarta.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
    suspend operator fun invoke(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    ): Resource<List<SpecTransaction>> {
        return retryWithDelay {
            repository.getTransactionFromPeriod(accountId, startDate, endDate)
        }
    }
}