package com.abrosimov.api.service.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.abrosimov.api.models.dbo.TransactionEntity

interface TransactionDao {
    @Query("SELECT * FROM transactions WHERE isDeleted = 0")
    suspend fun getAll(): List<TransactionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transactions: List<TransactionEntity>)

    @Query("SELECT * FROM transactions WHERE serverId IS NULL AND isDeleted = 0")
    suspend fun getUnsyncedNew(): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE lastSyncedAt IS NULL OR localUpdatedAt > lastSyncedAt")
    suspend fun getUnsynced(): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE isDeleted = 0 AND transactionDate BETWEEN :startDate AND :endDate")
    suspend fun getByPeriod(startDate: Long, endDate: Long): List<TransactionEntity>

    @Update
    suspend fun update(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE localId = :localId")
    suspend fun getByLocalId(localId: Int): TransactionEntity?

    @Query("SELECT * FROM transactions WHERE serverId = :serverId")
    suspend fun getByServerId(serverId: Int): TransactionEntity?
}