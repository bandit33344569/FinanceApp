package com.abrosimov.api.repository

import com.abrosimov.api.models.dto.CategoryDto
import com.abrosimov.utils.models.Resource

interface CategoriesRepository {
    suspend fun getCategories(): Resource<List<CategoryDto>>
    suspend fun getCategoriesByType(isIncome: Boolean): Resource<List<CategoryDto>>
}