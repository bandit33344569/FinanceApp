package com.abrosimov.api.service

import com.abrosimov.api.models.dto.CategoryDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoriesApi {
    //список всех категорий: расходов и доходов
    @GET("categories")
    suspend fun getAllCategories(): Response<List<CategoryDto>>

    //список категроий по типу
    @GET("categories/type/{isIncome}")
    suspend fun getCategoriesByType(
        @Path("isIncome") isIncome: Boolean
    ): Response<List<CategoryDto>>
}