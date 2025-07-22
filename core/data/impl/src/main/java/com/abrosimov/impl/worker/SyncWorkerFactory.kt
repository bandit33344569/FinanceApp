package com.abrosimov.impl.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.abrosimov.api.repository.AccountRepository
import com.abrosimov.api.repository.CategoriesRepository
import com.abrosimov.api.repository.TransactionRepository
import javax.inject.Inject
import javax.inject.Provider

class SyncWorkerFactory @Inject constructor(
    private val accountRepository: AccountRepository,
    private val categoriesRepository: CategoriesRepository,
    private val transactionRepository: TransactionRepository
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            SyncWorker::class.java.name -> SyncWorker(
                appContext,
                workerParameters,
                accountRepository,
                categoriesRepository,
                transactionRepository
            )

            else -> null
        }
    }
}