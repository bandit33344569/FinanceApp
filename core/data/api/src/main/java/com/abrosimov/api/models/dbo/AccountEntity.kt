package com.abrosimov.api.models.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abrosimov.api.models.dto.AccountDto

@Entity(tableName = "account")
data class AccountEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String,
    val localUpdatedAt: String?,
)

fun AccountEntity.toDto(): AccountDto {
    return AccountDto(
        id = id,
        userId = userId,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}