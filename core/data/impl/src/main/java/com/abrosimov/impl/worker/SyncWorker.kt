package com.abrosimov.impl.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.abrosimov.api.repository.AccountRepository
import com.abrosimov.api.repository.CategoriesRepository
import com.abrosimov.api.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SyncWorker @Inject constructor(
    context: Context,
    params: WorkerParameters,
    private val accountRepository: AccountRepository,
    private val categoriesRepository: CategoriesRepository,
    private val transactionRepository: TransactionRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            Log.i("SyncWorker", "Начало синхронизации...")
            accountRepository.syncAccount()
            Log.i("SyncWorker", "Аккаунты синхронизированы")
            categoriesRepository.syncCategories()
            Log.i("SyncWorker", "Категории синхронизированы")
            transactionRepository.syncWithServer()
            Log.i("SyncWorker", "транзакции синхронизированы")
            Result.success()
        } catch (e: Exception) {
            Log.e("SyncWorker", "Ошибка синхронизации: ${e.message}")
            Result.retry()
        }
    }
}