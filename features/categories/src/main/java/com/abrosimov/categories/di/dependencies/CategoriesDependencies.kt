package com.abrosimov.categories.di.dependencies

import com.abrosimov.api.repository.CategoriesRepository

interface CategoriesDependencies {
    val categoryRepository: CategoriesRepository
}