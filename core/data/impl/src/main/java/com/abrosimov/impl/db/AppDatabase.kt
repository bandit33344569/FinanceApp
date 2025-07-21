package com.abrosimov.impl.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abrosimov.api.models.dbo.AccountEntity
import com.abrosimov.api.models.dbo.CategoryEntity
import com.abrosimov.api.models.dbo.TransactionEntity
import com.abrosimov.api.service.local.AccountDao
import com.abrosimov.api.service.local.CategoryDao
import com.abrosimov.api.service.local.TransactionDao

@Database(
    entities = [TransactionEntity::class, CategoryEntity::class, AccountEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
    abstract fun accountDao(): AccountDao
}