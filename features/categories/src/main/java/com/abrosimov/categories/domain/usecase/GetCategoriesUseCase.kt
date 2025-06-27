package com.abrosimov.categories.domain.usecase

import com.abrosimov.categories.domain.repository.CategoriesRepository
import com.abrosimov.core.domain.Resource
import com.abrosimov.core.domain.models.Category
import com.abrosimov.core.domain.retryWithDelay
import javax.inject.Inject

/**
 * UseCase для получения списка категорий с поддержкой повторных попыток.
 *
 * Использует [CategoriesRepository] для получения данных и применяет логику повтора с задержкой
 * в случае сетевых или временных ошибок.
 *
 * @property repository Репозиторий, предоставляющий данные о категориях.
 */
class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    suspend operator fun invoke(): Resource<List<Category>> {
        return retryWithDelay {
            repository.getCategories()
        }
    }
}