package com.abrosimov.api.models.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "account")
data class AccountEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: Long,
    val updatedAt: Long,
    val localUpdatedAt: Long?,
)