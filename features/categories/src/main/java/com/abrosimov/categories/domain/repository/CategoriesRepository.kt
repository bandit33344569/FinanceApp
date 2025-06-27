package com.abrosimov.categories.domain.repository

import com.abrosimov.core.domain.Resource
import com.abrosimov.core.domain.models.Category

interface CategoriesRepository {
    suspend fun getCategories(): Resource<List<Category>>
}