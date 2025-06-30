package com.abrosimov.categories.data.repository

import com.abrosimov.categories.data.api.CategoriesApi
import com.abrosimov.categories.domain.repository.CategoriesRepository
import com.abrosimov.core.data.mappers.toDomain
import com.abrosimov.core.domain.Resource
import com.abrosimov.core.domain.map
import com.abrosimov.network.apiCall.safeApiCall
import com.abrosimov.network.networkMonitor.NetworkMonitor
import com.abrosimov.core.domain.models.Category
import javax.inject.Inject

/**
 * Реализация репозитория [CategoriesRepository], предоставляющая данные о категориях из сети.
 *
 * Использует [CategoriesApi] для выполнения сетевых запросов и [NetworkMonitor] для проверки подключения.
 * Обрабатывает ответы от сервера с помощью [safeApiCall] и преобразует DTO в доменные модели через `toDomain()`.
 *
 * @property networkMonitor Мониторинг состояния интернет-соединения.
 * @property api API-интерфейс для работы с категориями.
 */
class CategoriesRepositoryImpl @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val api: CategoriesApi
) : CategoriesRepository {
    override suspend fun getCategories(): Resource<List<Category>> {
        return safeApiCall(networkMonitor) { api.getAllCategories() }
            .map { list -> list.map { it.toDomain() } }
    }
}