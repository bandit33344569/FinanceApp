package com.abrosimov.api.models.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abrosimov.api.models.dto.CategoryDto

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
)

fun CategoryEntity.toDto(): CategoryDto{
    return CategoryDto(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}