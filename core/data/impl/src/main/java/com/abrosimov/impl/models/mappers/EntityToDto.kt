package com.abrosimov.impl.models.mappers

import com.abrosimov.api.models.dbo.CategoryEntity
import com.abrosimov.api.models.dto.CategoryDto


fun CategoryEntity.toDto(): CategoryDto {
    return CategoryDto(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}