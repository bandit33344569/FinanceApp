package com.abrosimov.impl.models.mappers

import com.abrosimov.api.models.dbo.CategoryEntity
import com.abrosimov.api.models.dto.CategoryDto

fun CategoryDto.toEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}