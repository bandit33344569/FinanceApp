package com.abrosimov.impl.repository

import com.abrosimov.api.models.dbo.toDto
import com.abrosimov.api.models.dto.CategoryDto
import com.abrosimov.api.models.dto.toEntity
import com.abrosimov.api.repository.CategoriesRepository
import com.abrosimov.api.service.local.CategoryDao
import com.abrosimov.api.service.remote.CategoriesApi
import com.abrosimov.impl.networkMonitor.NetworkMonitor
import com.abrosimov.impl.safeApiCall
import com.abrosimov.utils.models.Resource
import javax.inject.Inject


class CategoriesRepositoryImpl @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val api: CategoriesApi,
    private val categoryDao: CategoryDao
) : CategoriesRepository {

    override suspend fun getCategories(): Resource<List<CategoryDto>> {
        val local = categoryDao.getAll()

        if (isNetworkAvailable()) {
            try {
                val response = api.getAllCategories()
                if (response.isSuccessful && response.body().orEmpty().isNotEmpty()) {
                    val remote = response.body()!!
                    val entities = remote.map { it.toEntity() }
                    categoryDao.insertAll(entities)
                    return Resource.Success(entities.map { it.toDto() })
                }
            } catch (e: Exception) {
            }
        }

        return if (local.isNotEmpty()) {
            Resource.Success(local.map { it.toDto() })
        } else {
            Resource.Error("Категории не найдены")
        }
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): Resource<List<CategoryDto>> {
        val local = categoryDao.getByType(isIncome)

        if (isNetworkAvailable()) {
            try {
                val response = api.getCategoriesByType(isIncome)
                if (response.isSuccessful && response.body().orEmpty().isNotEmpty()) {
                    val remote = response.body()!!
                    val entities = remote.map { it.toEntity() }
                    categoryDao.insertAll(entities)
                    return Resource.Success(entities.map { it.toDto() })
                }
            } catch (e: Exception) {
            }
        }

        return if (local.isNotEmpty()) {
            Resource.Success(local.map { it.toDto() })
        } else {
            Resource.Error("Категории не найдены")
        }
    }

    private suspend fun isNetworkAvailable(): Boolean {
        return networkMonitor.isOnline()
    }
}