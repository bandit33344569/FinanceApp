package com.abrosimov.impl.service

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.abrosimov.api.repository.AccountRepository
import com.abrosimov.api.repository.CategoriesRepository
import com.abrosimov.api.repository.TransactionRepository
import com.abrosimov.api.service.local.TransactionDao
import javax.inject.Inject

class SyncWorkerFactory @Inject constructor(
    private val accountRepo: AccountRepository,
    private val transactionRepo: TransactionRepository,
    private val categoryRepo: CategoriesRepository,
    private val transactionDao: TransactionDao
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            SyncWorker::class.java.name -> SyncWorker(
                context = appContext,
                params = workerParameters,
                accountRepo = accountRepo,
                transactionRepo = transactionRepo,
                categoryRepo = categoryRepo,
                transactionDao = transactionDao
            )
            else -> null
        }
    }
}