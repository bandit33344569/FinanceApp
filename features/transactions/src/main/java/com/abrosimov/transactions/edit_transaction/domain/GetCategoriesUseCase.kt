package com.abrosimov.transactions.edit_transaction.domain

import com.abrosimov.api.repository.CategoriesRepository
import com.abrosimov.impl.models.Category
import com.abrosimov.impl.models.mappers.toDomain
import com.abrosimov.utils.models.Resource
import com.abrosimov.utils.models.map
import com.abrosimov.utils.retryWithDelay
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) {
    suspend fun getIncomeCategories(): Resource<List<Category>> {
        return retryWithDelay {
            categoriesRepository.getCategoriesByType(true)
                .map { listDto -> listDto.map { it.toDomain() } }
        }
    }

    suspend fun getExpenseCategories(): Resource<List<Category>> {
        return retryWithDelay {
            categoriesRepository.getCategoriesByType(false)
                .map { listDto -> listDto.map { it.toDomain() } }
        }
    }
}