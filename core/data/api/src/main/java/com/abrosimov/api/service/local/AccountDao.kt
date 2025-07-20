package com.abrosimov.api.service.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.abrosimov.api.models.dbo.AccountEntity

interface AccountDao {
    @Query("SELECT * FROM account LIMIT 1")
    suspend fun getAccount(): AccountEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: AccountEntity)

    @Update
    suspend fun update(account: AccountEntity)

    @Query("SELECT * FROM account WHERE localUpdatedAt > updatedAt OR localUpdatedAt IS NOT NULL")
    suspend fun getUnsynced(): AccountEntity?
}