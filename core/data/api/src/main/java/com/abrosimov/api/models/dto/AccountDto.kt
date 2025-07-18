package com.abrosimov.api.models.dto

import com.abrosimov.api.models.dbo.AccountEntity
import com.abrosimov.api.models.requests.AccountUpdateRequest

data class AccountDto(
    val id:Int,
    val userId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String,
)

fun AccountDto.toEntity(): AccountEntity{
    return AccountEntity(
        id = id,
        userId = userId,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = createdAt,
        updatedAt = updatedAt,
        localUpdatedAt = null
    )
}

fun AccountDto.toAccountRequest(): AccountUpdateRequest{
    return AccountUpdateRequest(
        name = name,
        balance = balance,
        currency = currency
    )
}
