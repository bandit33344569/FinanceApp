package com.abrosimov.api.service.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abrosimov.api.models.dbo.CategoryEntity

interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<CategoryEntity>)

    @Query("SELECT * FROM categories WHERE isIncome = :isIncome")
    suspend fun getByType(isIncome: Boolean): List<CategoryEntity>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getById(id: Int): CategoryEntity

    @Query("SELECT * FROM categories")
    suspend fun getAll(): List<CategoryEntity>

    @Query("DELETE FROM categories")
    suspend fun clear()
}