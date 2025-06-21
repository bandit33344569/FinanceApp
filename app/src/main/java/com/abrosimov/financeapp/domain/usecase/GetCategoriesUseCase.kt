package com.abrosimov.financeapp.domain.usecase

import com.abrosimov.financeapp.domain.models.Category
import com.abrosimov.financeapp.domain.repo.FinanceRepository
import com.abrosimov.financeapp.domain.repo.Resource
import com.abrosimov.financeapp.domain.repo.retryWithDelay
import jakarta.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
    suspend operator fun invoke(): Resource<List<Category>> {
        return retryWithDelay {
            repository.getCategories()
        }
    }
}