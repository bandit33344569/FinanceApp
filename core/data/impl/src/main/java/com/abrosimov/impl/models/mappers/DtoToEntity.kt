package com.abrosimov.impl.models.mappers

import com.abrosimov.api.models.dbo.AccountEntity
import com.abrosimov.api.models.dbo.CategoryEntity
import com.abrosimov.api.models.dto.AccountDto
import com.abrosimov.api.models.dto.CategoryDto
import com.abrosimov.api.models.dto.requests.AccountUpdateRequest
import com.abrosimov.utils.dateutils.DateUtils

fun CategoryDto.toEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
}

fun AccountDto.toEntity(localUpdatedAt: String): AccountEntity {
    return AccountEntity(
        id = id,
        userId = userId,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = DateUtils.isoStringToLong(createdAt),
        updatedAt = DateUtils.isoStringToLong(updatedAt),
        localUpdatedAt = DateUtils.isoStringToLong(localUpdatedAt)
    )
}

fun AccountUpdateRequest.toEntity(currentAccountEntity: AccountEntity): AccountEntity {
    return AccountEntity(
        id = currentAccountEntity.id,
        userId = currentAccountEntity.userId,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = currentAccountEntity.createdAt,
        updatedAt = currentAccountEntity.updatedAt,
        localUpdatedAt = System.currentTimeMillis()
    )
}