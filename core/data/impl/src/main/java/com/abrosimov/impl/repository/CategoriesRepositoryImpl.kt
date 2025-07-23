package com.abrosimov.impl.repository

import com.abrosimov.api.models.dto.CategoryDto
import com.abrosimov.api.repository.CategoriesRepository
import com.abrosimov.api.service.local.CategoryDao
import com.abrosimov.api.service.remote.CategoriesApi
import com.abrosimov.impl.models.mappers.toDto
import com.abrosimov.impl.models.mappers.toEntity
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
    private val api: CategoriesApi,
    private val categoryDao: CategoryDao,
) : CategoriesRepository {
    override suspend fun getCategories(): Resource<List<CategoryDto>> {
        return try {
            val categories = categoryDao.getAll().map { it.toDto() }
            Resource.Success(categories)
        } catch (e: Exception) {
            Resource.Error("Failed to fetch categories from database: ${e.message}")
        }
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): Resource<List<CategoryDto>> {
        return try {
            val categories = categoryDao.getByType(isIncome).map { it.toDto() }
            Resource.Success(categories)
        } catch (e: Exception) {
            Resource.Error("Failed to fetch categories by type from database: ${e.message}")
        }
    }

    override suspend fun syncCategories() {
        if (!networkMonitor.isOnline()) return
        when (val result = safeApiCall(networkMonitor) { api.getAllCategories() }) {
            is Resource.Error -> return
            Resource.Loading -> return
            is Resource.Success -> categoryDao.insertAll(result.data.map { it.toEntity() })
        }
    }
}