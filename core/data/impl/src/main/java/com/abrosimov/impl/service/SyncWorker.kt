package com.abrosimov.impl.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.abrosimov.api.models.dbo.toCreateRequest
import com.abrosimov.api.repository.AccountRepository
import com.abrosimov.api.repository.CategoriesRepository
import com.abrosimov.api.repository.TransactionRepository
import com.abrosimov.api.service.local.TransactionDao
import com.abrosimov.utils.dateutils.DateUtils
import com.abrosimov.utils.models.Resource
import com.abrosimov.utils.models.Resource.Loading
import com.abrosimov.utils.models.Resource.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SyncWorker @Inject constructor(
    context: Context,
    params: WorkerParameters,
    private val accountRepo: AccountRepository,
    private val transactionRepo: TransactionRepository,
    private val categoryRepo: CategoriesRepository,
    private val transactionDao: TransactionDao
) : CoroutineWorker(context, params) {

    companion object {
        const val TAG = "SyncWorker"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val accountResult = syncAccount(accountRepo)
        syncCategories(categoryRepo)
        syncTransactions(transactionRepo, accountRepo)
        Result.success()
    }

    private suspend fun syncAccount(accountRepo: AccountRepository): Result {
        val result = accountRepo.getAccount()
        return when (result) {
            is Success -> Result.success()
            is Resource.Error -> Result.failure()
            is Loading -> Result.retry()
        }
    }

    private suspend fun syncCategories(categoryRepo: CategoriesRepository): Result {
        return try {
            val result = categoryRepo.getCategories()
            when (result) {
                is Success -> Result.success()
                is Resource.Error -> Result.failure()
                is Loading -> Result.retry()
            }
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private suspend fun syncTransactions(
        transactionRepo: TransactionRepository,
        accountRepo: AccountRepository
    ): Result {
        return try {
            val accountId = (accountRepo.getAccount() as? Success)?.data?.id
                ?: return Result.failure()

            val localChangesResult = transactionRepo.getLocalChanges()
            if (localChangesResult is Error) {
                return Result.success()
            }

            val localChanges = (localChangesResult as Success).data

            for (tx in localChanges) {
                if (tx.serverId == null) {
                    val request = tx.toCreateRequest(accountId)
                    val createResult = transactionRepo.createTransaction(request)
                    if (createResult is Success) {
                        transactionDao.update(tx.copy(lastSyncedAt = DateUtils.getCurrentIsoDate()))
                    }
                } else {
                    val request = tx.toCreateRequest(accountId)
                    val updateResult = transactionRepo.updateTransaction(tx.localId, request)
                    if (updateResult is Success) {
                        transactionDao.update(tx.copy(lastSyncedAt = DateUtils.getCurrentIsoDate()))
                    }
                }
            }

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

}
