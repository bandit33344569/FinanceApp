package com.abrosimov.api.models.dto

import com.abrosimov.api.models.dbo.CategoryEntity

data class CategoryDto(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
)

fun CategoryDto.toEntity(): CategoryEntity{
    return CategoryEntity(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}