package com.abrosimov.impl.repository

import com.abrosimov.api.models.dto.CategoryDto
import com.abrosimov.api.repository.CategoriesRepository
import com.abrosimov.api.service.CategoriesApi
import com.abrosimov.impl.networkMonitor.NetworkMonitor
import com.abrosimov.impl.safeApiCall
import com.abrosimov.utils.models.Resource
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
    override suspend fun getCategories(): Resource<List<CategoryDto>> {

        return safeApiCall(networkMonitor) { api.getAllCategories() }
    }
}