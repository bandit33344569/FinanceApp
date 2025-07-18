package com.abrosimov.api.models.dbo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.abrosimov.api.models.dto.SpecTransactionDto
import com.abrosimov.api.models.dto.TransactionDto
import com.abrosimov.api.models.requests.TransactionRequest


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
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String,
    val localUpdatedAt: String?,
    val isDeleted: Boolean,
    val lastSyncedAt: String?
)

fun TransactionEntity.toSpecTransactionDto(categoryEntity: CategoryEntity): SpecTransactionDto {
    return SpecTransactionDto(
        id = localId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt,
        category = categoryEntity.toDto()
    )
}

fun TransactionEntity.toTransactionDto(categoryId: Int, accountId: Int): TransactionDto {
    return TransactionDto(
        id = localId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt,
        accountId = accountId,
        categoryId = categoryId
    )
}

fun TransactionEntity.toCreateRequest(accountId: Int): TransactionRequest {
    return TransactionRequest(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment
    )
}