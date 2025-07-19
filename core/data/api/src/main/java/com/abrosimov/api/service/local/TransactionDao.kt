package com.abrosimov.api.service.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.abrosimov.api.models.dbo.TransactionEntity

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transactions: List<TransactionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(transactions: List<TransactionEntity>)

    @Update
    suspend fun update(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE transactionDate BETWEEN :startDate AND :endDate")
    suspend fun getTransactionsFromPeriod(
        startDate: String,
        endDate: String
    ): List<TransactionEntity>

    @Query("UPDATE transactions SET lastSyncedAt = :timestamp WHERE localId = :localId")
    suspend fun markAsSynced(localId: Int, timestamp: String)

    @Query("SELECT * FROM transactions WHERE serverId = :serverId")
    suspend fun getByServerId(serverId: Int): TransactionEntity?

    @Query("SELECT * FROM transactions WHERE localId = :localId")
    suspend fun getByLocalId(localId: Int): TransactionEntity?

    @Query("SELECT * FROM transactions WHERE serverId IS NULL")
    suspend fun getUnsyncedTransactions(): List<TransactionEntity>

    @Query("UPDATE transactions SET serverId = :serverId WHERE localId = :localId")
    suspend fun updateServerId(localId: Int, serverId: Int)

    @Delete
    suspend fun delete(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE localUpdatedAt > updatedAt OR serverId IS NULL")
    suspend fun getLocalChanges(): List<TransactionEntity>
}