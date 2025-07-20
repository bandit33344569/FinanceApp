package com.abrosimov.impl.models.mappers

import com.abrosimov.api.models.dbo.AccountEntity
import com.abrosimov.api.models.dbo.CategoryEntity
import com.abrosimov.api.models.dto.AccountDto
import com.abrosimov.api.models.dto.CategoryDto
import com.abrosimov.utils.dateutils.DateUtils


fun CategoryEntity.toDto(): CategoryDto {
    return CategoryDto(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}

fun AccountEntity.toDto(): AccountDto {
    return AccountDto(
        id = id,
        userId = userId,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = DateUtils.longToIsoString(createdAt),
        updatedAt = DateUtils.longToIsoString(updatedAt)
    )
}

