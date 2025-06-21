package com.abrosimov.financeapp.domain.usecase

import com.abrosimov.financeapp.domain.models.Account
import com.abrosimov.financeapp.domain.repo.FinanceRepository
import com.abrosimov.financeapp.domain.repo.Resource
import com.abrosimov.financeapp.domain.repo.retryWithDelay
import jakarta.inject.Inject

class GetAccountUseCase@Inject constructor(
    private val repository: FinanceRepository
) {
    suspend operator fun invoke(): Resource<Account> {
        return retryWithDelay {
            repository.getAccount()
        }
    }
}