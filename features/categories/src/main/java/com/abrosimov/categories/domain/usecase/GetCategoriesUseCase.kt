package com.abrosimov.categories.domain.usecase

import com.abrosimov.api.repository.CategoriesRepository
import com.abrosimov.impl.models.Category
import com.abrosimov.impl.models.mappers.toDomain
import com.abrosimov.utils.models.Resource
import com.abrosimov.utils.models.map
import com.abrosimov.utils.retryWithDelay
import javax.inject.Inject

/**
 * UseCase для получения списка категорий с поддержкой повторных попыток.
 *
 * Использует [com.abrosimov.api.repository.CategoriesRepository] для получения данных и применяет логику повтора с задержкой
 * в случае сетевых или временных ошибок.
 *
 * @property repository Репозиторий, предоставляющий данные о категориях.
 */
class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    suspend operator fun invoke(): Resource<List<Category>> {
        return retryWithDelay {
            repository.getCategories().map { dtoList ->
                dtoList.map { it.toDomain() }  }
        }
    }
}