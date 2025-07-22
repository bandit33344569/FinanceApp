package com.abrosimov.impl.repository

import android.util.Log
import androidx.work.ListenableWorker
import com.abrosimov.api.models.dbo.TransactionEntity
import com.abrosimov.api.models.dto.SpecTransactionDto
import com.abrosimov.api.models.dto.TransactionDto
import com.abrosimov.api.models.dto.requests.TransactionRequest
import com.abrosimov.api.repository.TransactionRepository
import com.abrosimov.api.service.local.AccountDao
import com.abrosimov.api.service.local.CategoryDao
import com.abrosimov.api.service.local.TransactionDao
import com.abrosimov.api.service.remote.TransactionsApi
import com.abrosimov.impl.models.mappers.toCreateEntity
import com.abrosimov.impl.models.mappers.toCreateRequest
import com.abrosimov.impl.models.mappers.toEntity
import com.abrosimov.impl.models.mappers.toSpecTransactionDto
import com.abrosimov.impl.models.mappers.toTransactionDto
import com.abrosimov.impl.models.mappers.toUpdateEntity
import com.abrosimov.impl.networkMonitor.NetworkMonitor
import com.abrosimov.impl.safeApiCall
import com.abrosimov.utils.dateutils.DateUtils
import com.abrosimov.utils.models.Resource
import javax.inject.Inject

/**
 * Реализация репозитория [TransactionRepository], предоставляющая функционал для получения
 * транзакций за определённый период с использованием удалённого API.
 *
 * Использует [NetworkMonitor] для проверки наличия интернет-соединения перед выполнением запросов,
 * и [TransactionsApi] для выполнения сетевых вызовов. Результаты мапятся в доменные модели
 *
 * @property networkMonitor Монитор сети, используемый для проверки доступности интернета.
 * @property api Сервис для взаимодействия с API транзакций.
 */
class TransactionRepositoryImpl @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val api: TransactionsApi,
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao,
    private val accountDao: AccountDao,
) : TransactionRepository {
    override suspend fun getTransactionFromPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): Resource<List<SpecTransactionDto>> {
        return try {
            val start =
                startDate?.let { DateUtils.isoStringToLong(it) } ?: DateUtils.isoStringToLong(
                    DateUtils.dateToIsoString(
                        DateUtils.getStartOfMonth(
                            DateUtils.today()
                        )
                    )
                )
            val end = endDate?.let { DateUtils.isoStringToLong(it) } ?: DateUtils.isoStringToLong(
                DateUtils.dateToIsoString(
                    DateUtils.getEndOfDay(
                        DateUtils.today()
                    )
                )
            )
            val localTransactions = transactionDao.getByPeriod(start, end)

            if (networkMonitor.isOnline()) {
                fetchAndSaveServerTransactions(accountId, startDate, endDate)
            } else if (localTransactions.isEmpty()) {
                return Resource.Success(emptyList())
            }

            val updatedTransactions = transactionDao.getByPeriod(start, end)
            val result = updatedTransactions.map { transaction ->
                transaction.toSpecTransactionDto(categoryDao.getById(transaction.categoryId))
            }

            if (result.isEmpty()) {
                Resource.Error("No transactions from selected period")
            }
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error("Failed to fetch transactions from database: ${e.message}")
        }
    }

    override suspend fun getTransactionById(transactionId: Int): Resource<SpecTransactionDto> {
        return when (val transaction = transactionDao.getByLocalId(transactionId)) {
            is TransactionEntity -> Resource.Success(
                transaction.toSpecTransactionDto(
                    categoryDao.getById(transaction.categoryId)
                )
            )

            else -> Resource.Error(message = "transaction not found")
        }
    }

    override suspend fun updateTransaction(
        transactionId: Int,
        transactionRequest: TransactionRequest
    ): Resource<SpecTransactionDto> {
        return try {
            val currentTransaction = transactionDao.getByLocalId(transactionId)
            if (currentTransaction == null) {
                Resource.Error("Transaction with ID $transactionId not found in database")
            } else {
                val updatedTransaction = transactionRequest.toUpdateEntity(
                    localUpdatedAt = System.currentTimeMillis(),
                    currentTransaction = currentTransaction
                )
                transactionDao.update(updatedTransaction)
                val category = categoryDao.getById(updatedTransaction.categoryId)
                Resource.Success(updatedTransaction.toSpecTransactionDto(category))
            }
        } catch (e: Exception) {
            Resource.Error("Failed to update transaction in database: ${e.message}")
        }
    }

    override suspend fun deleteTransaction(transactionId: Int) {
        TODO()
    }

    override suspend fun createTransaction(transactionRequest: TransactionRequest): Resource<TransactionDto> {
        return try {
            val transactionEntity = transactionRequest.toCreateEntity()
            val localId = transactionDao.insert(transactionEntity).toInt()
            val insertedTransaction = transactionDao.getByLocalId(localId)
            if (insertedTransaction == null) {
                return Resource.Error("Failed to insert transaction into database")
            }
            Resource.Success(insertedTransaction.toTransactionDto(insertedTransaction.categoryId))
        } catch (e: Exception) {
            Resource.Error("Failed to create transaction: ${e.message}")
        }
    }

    override suspend fun syncWithServer() {
        if (!networkMonitor.isOnline()) {
            Log.w("TransactionRepository", "No internet connection, retrying sync")
            return
        }

        try {
            val accountId = accountDao.getAccount()?.id
            if (accountId == null) {
                Log.e("TransactionRepository", "No account found for synchronization")
                return
            }

            // 1. Синхронизируем локально добавленные транзакции (serverId == null)
            val unsyncedNewTransactions = transactionDao.getUnsyncedNew()
            Log.i("TransactionsRepository","unsyced transactions = $unsyncedNewTransactions")
            unsyncedNewTransactions.forEach { localTransaction ->
                val request = localTransaction.toCreateRequest(accountId)
                val createResult = safeApiCall(networkMonitor) {
                    api.createNewTransaction(request)
                }
                if (createResult is Resource.Success) {
                    transactionDao.update(
                        localTransaction.copy(
                            serverId = createResult.data.id,
                            updatedAt = DateUtils.isoStringToLong(createResult.data.updatedAt),
                            createdAt = DateUtils.isoStringToLong(createResult.data.createdAt),
                            lastSyncedAt = System.currentTimeMillis(),
                            localUpdatedAt = null
                        )
                    )
                } else {
                    Log.e(
                        "TransactionRepository",
                        "Failed to create transaction ${localTransaction.localId} on server"
                    )
                }
            }

            val unsyncedTransactions = transactionDao.getUnsynced()
            unsyncedTransactions.forEach { localTransaction ->
                localTransaction.serverId?.let { serverId ->
                    val serverResult = safeApiCall(networkMonitor) {
                        api.getTransactionFromId(serverId)
                    }
                    if (serverResult is Resource.Success) {
                        val serverTransaction = serverResult.data
                        val serverUpdatedAt = DateUtils.isoStringToLong(serverTransaction.updatedAt)
                        val localUpdatedAt =
                            localTransaction.localUpdatedAt ?: localTransaction.updatedAt
                        if (serverUpdatedAt > localUpdatedAt) {
                            transactionDao.update(
                                serverTransaction.toEntity(localTransaction.localId).copy(
                                    lastSyncedAt = System.currentTimeMillis(),
                                    localUpdatedAt = DateUtils.isoStringToLong(serverTransaction.updatedAt)
                                )
                            )
                            Log.i(
                                "TransactionRepository",
                                "Updated local transaction ${localTransaction.localId} with server data"
                            )
                        } else if (localUpdatedAt > serverUpdatedAt) {
                            val request = localTransaction.toCreateRequest(accountId)
                            val updateResult = safeApiCall(networkMonitor) {
                                api.updateTransaction(serverId, request)
                            }
                            if (updateResult is Resource.Success) {
                                transactionDao.update(
                                    localTransaction.copy(
                                        updatedAt = DateUtils.isoStringToLong(updateResult.data.updatedAt),
                                        lastSyncedAt = System.currentTimeMillis(),
                                        localUpdatedAt = DateUtils.isoStringToLong(updateResult.data.updatedAt)
                                    )
                                )
                                Log.i(
                                    "TransactionRepository",
                                    "Synced local transaction ${localTransaction.localId} to server"
                                )
                            } else {
                                Log.e(
                                    "TransactionRepository",
                                    "Failed to update transaction ${localTransaction.localId} on server"
                                )
                            }
                        } else {
                            transactionDao.update(
                                localTransaction.copy(
                                    lastSyncedAt = System.currentTimeMillis(),
                                    localUpdatedAt = serverUpdatedAt
                                )
                            )
                        }
                    } else {
                        Log.e(
                            "TransactionRepository",
                            "Failed to fetch transaction $serverId from server"
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TransactionRepository", "Sync failed: ${e.message}")
        }

    }

    private suspend fun fetchAndSaveServerTransactions(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): Resource<Unit> {
        return try {
            val serverStart = startDate?.let {
                if (it.contains("T")) DateUtils.getDateStringFromIso(it) else it
            } ?: DateUtils.dateToServerFormat(DateUtils.getStartOfMonth(DateUtils.today()))
            val serverEnd = endDate?.let {
                if (it.contains("T")) DateUtils.getDateStringFromIso(it) else it
            } ?: DateUtils.dateToServerFormat(DateUtils.getEndOfDay(DateUtils.today()))
            Log.i("FetAndSaveServerTransactions","Server query: start=$serverStart, end=$serverEnd")
            val result = safeApiCall(networkMonitor) {
                api.getTransactionsFromPeriod(accountId, serverStart, serverEnd)
            }
            if (result is Resource.Success && result.data.isNotEmpty()) {
                val serverTransactions = result.data.map { serverTransaction ->
                    val existingTransaction =
                        serverTransaction.id.let { transactionDao.getByServerId(it) }
                    if (existingTransaction != null) {
                        val serverUpdatedAt = DateUtils.isoStringToLong(serverTransaction.updatedAt)
                        val localUpdatedAt =
                            existingTransaction.localUpdatedAt ?: existingTransaction.updatedAt
                        if (localUpdatedAt > serverUpdatedAt) {
                            existingTransaction
                        } else {
                            serverTransaction.toEntity(existingTransaction.localId)
                        }
                    } else {
                        serverTransaction.toEntity(localId = 0)
                    }
                }
                transactionDao.insertAll(serverTransactions)
                Resource.Success(Unit)
            } else if (result is Resource.Error) {
                Resource.Error(result.message)
            } else {
                Resource.Success(Unit)
            }
        } catch (e: Exception) {
            Resource.Error("Failed to fetch and save transactions: ${e.message}")
        }
    }
}