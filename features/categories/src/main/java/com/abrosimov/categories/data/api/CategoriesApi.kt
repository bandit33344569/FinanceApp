package com.abrosimov.categories.data.api

import com.abrosimov.core.data.models.dto.CategoryDto
import retrofit2.Response
import retrofit2.http.GET

interface CategoriesApi {
    //список всех категорий: расходов и доходов
    @GET("categories")
    suspend fun getAllCategories(): Response<List<CategoryDto>>

    //список категроий по типу
    @GET("categories/type/{isIncome}")
    suspend fun getCategoriesByType(): Response<List<CategoryDto>>
}