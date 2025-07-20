package com.abrosimov.api.models.dbo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    foreignKeys = [ForeignKey(
        entity = CategoryEntity::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Int,
    val serverId: Int?,
    val categoryId: Int,
    val amount: String,
    val transactionDate: Long,
    val comment: String?,
    val createdAt: Long,
    val updatedAt: Long,
    val localUpdatedAt: Long?,
    val isDeleted: Boolean,
    val lastSyncedAt: Long?
)
