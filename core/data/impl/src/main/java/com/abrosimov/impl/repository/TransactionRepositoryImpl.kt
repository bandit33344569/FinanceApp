package com.abrosimov.impl.repository

import com.abrosimov.api.models.dbo.TransactionEntity
import com.abrosimov.api.models.dbo.toSpecTransactionDto
import com.abrosimov.api.models.dbo.toTransactionDto
import com.abrosimov.api.models.dto.SpecTransactionDto
import com.abrosimov.api.models.dto.TransactionDto
import com.abrosimov.api.models.dto.toEntity
import com.abrosimov.api.models.requests.TransactionRequest
import com.abrosimov.api.models.responses.TransactionResponse
import com.abrosimov.api.models.responses.toEntity
import com.abrosimov.api.models.responses.toSpecTransactionDto
import com.abrosimov.api.repository.TransactionRepository
import com.abrosimov.api.service.local.CategoryDao
import com.abrosimov.api.service.local.TransactionDao
import com.abrosimov.api.service.remote.TransactionsApi
import com.abrosimov.impl.models.mappers.toDomain
import com.abrosimov.impl.networkMonitor.NetworkMonitor
import com.abrosimov.impl.safeApiCall
import com.abrosimov.utils.dateutils.DateUtils
import com.abrosimov.utils.models.Resource
import com.abrosimov.utils.models.Resource.*
import com.abrosimov.utils.models.map
import java.util.Date
import javax.inject.Inject


class TransactionRepositoryImpl @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val api: TransactionsApi,
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao,
    private val accountDetailsRepository: AccountDetailsRepository

) : TransactionRepository {
    private suspend fun getAccountId(): Int {
        return accountDetailsRepository.getAccountId()
    }

    private suspend fun isNetworkAvailable(): Boolean {
        return networkMonitor.isOnline()
    }

    override suspend fun getTransactionFromPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): Resource<List<SpecTransactionDto>> {
        val isOnline = isNetworkAvailable()
        val localStart =
            startDate ?: DateUtils.dateToIsoString(DateUtils.getStartOfDay(DateUtils.today()))
        val localEnd =
            endDate ?: DateUtils.dateToIsoString(DateUtils.getEndOfDay(DateUtils.today()))
        val localTransactions = transactionDao.getTransactionsFromPeriod(localStart, localEnd)

        if (!isOnline) {
            val specDtos = localTransactions.map { transaction ->
                val category = categoryDao.getById(transaction.categoryId)
                transaction.toSpecTransactionDto(category)
            }
            return Success(specDtos)
        }

        val serverStart =
            startDate?.let { DateUtils.getDateStringFromIso(it) } ?: DateUtils.dateToServerFormat(
                DateUtils.getStartOfDay(DateUtils.today())
            )
        val serverEnd =
            endDate?.let { DateUtils.getDateStringFromIso(it) } ?: DateUtils.dateToServerFormat(
                DateUtils.getEndOfDay(DateUtils.today())
            )
        val serverResult = safeApiCall(networkMonitor) {
            api.getTransactionsFromPeriod(accountId, serverStart, serverEnd)
        }

        return when (serverResult) {
            is Success -> {
                val serverTransactions = serverResult.data.map { dto ->
                    val existing = transactionDao.getByServerId(dto.id)
                    val localId = existing?.localId ?: 0
                    dto.toEntity(localId)
                }
                transactionDao.upsertAll(serverTransactions)
                val updatedTransactions =
                    transactionDao.getTransactionsFromPeriod(localStart, localEnd)

                val specDtos = updatedTransactions.map { transaction ->
                    val category = categoryDao.getById(transaction.categoryId)
                    transaction.toSpecTransactionDto(category)
                }
                Success(specDtos)
            }

            is Error -> serverResult
            Loading -> Loading
        }
    }

    override suspend fun getTransactionById(transactionId: Int): Resource<SpecTransactionDto> {
        val transactionEntity = transactionDao.getByLocalId(transactionId)
            ?: return Error("Транзакция не найдена")
        val categoryEntity = categoryDao.getById(transactionEntity.categoryId)
        val specTransactionDto = transactionEntity.toSpecTransactionDto(categoryEntity)
        return Success(specTransactionDto)
    }

    override suspend fun updateTransaction(
        transactionId: Int,
        transactionRequest: TransactionRequest
    ): Resource<SpecTransactionDto> {
        val isOnline = isNetworkAvailable()
        val localTime = DateUtils.getCurrentIsoDate()
        val accountId = getAccountId()

        val localEntity = transactionDao.getByLocalId(transactionId)
            ?: return Error("Транзакция не найдена локально")

        val updatedEntity = localEntity.copy(
            categoryId = transactionRequest.categoryId,
            amount = transactionRequest.amount,
            transactionDate = transactionRequest.transactionDate,
            comment = transactionRequest.comment,
            updatedAt = if (isOnline) localTime else localEntity.updatedAt,
            localUpdatedAt = localTime,
            lastSyncedAt = if (isOnline) null else localEntity.lastSyncedAt
        )

        transactionDao.update(updatedEntity)

        return if (isOnline) {
            val serverResult = safeApiCall(networkMonitor) {
                api.updateTransaction(updatedEntity.serverId!!, transactionRequest)
            }

            when (serverResult) {
                is Success -> {
                    val serverEntity = serverResult.data.toEntity(localId = transactionId)
                    val existing = transactionDao.getByServerId(serverEntity.serverId!!)
                    if (existing != null) {
                        transactionDao.update(serverEntity)
                    } else {
                        transactionDao.upsertAll(listOf(serverEntity))
                    }
                    val updatedFromDb = transactionDao.getByLocalId(transactionId)
                        ?: return Error("Не удалось получить обновлённую транзакцию")
                    Success(updatedFromDb.toSpecTransactionDto(categoryDao.getById(serverEntity.categoryId)))
                }

                is Error -> Error(serverResult.message)
                Loading -> Loading
            }
        } else {
            Success(updatedEntity.toSpecTransactionDto(categoryDao.getById(updatedEntity.categoryId)))
        }
    }

    override suspend fun deleteTransaction(transactionId: Int) {
        TODO()
    }

    override suspend fun createTransaction(transactionRequest: TransactionRequest): Resource<TransactionDto> {
        val isOnline = isNetworkAvailable()
        val localTime = DateUtils.getCurrentIsoDate()
        val accountId = getAccountId()

        val localEntity = TransactionEntity(
            localId = 0,
            serverId = null,
            categoryId = transactionRequest.categoryId,
            amount = transactionRequest.amount,
            transactionDate = transactionRequest.transactionDate,
            comment = transactionRequest.comment,
            createdAt = localTime,
            updatedAt = localTime,
            localUpdatedAt = localTime,
            isDeleted = false,
            lastSyncedAt = if (isOnline) localTime else null
        )

        val generatedLocalId = transactionDao.insert(localEntity).toInt()

        val insertedEntity = transactionDao.getByLocalId(generatedLocalId)
            ?: return Error("Не удалось сохранить транзакцию локально")

        return if (isOnline) {
            val serverEntityResult = safeApiCall(networkMonitor) {
                api.createNewTransaction(transactionRequest)
            }.map { it.toEntity(generatedLocalId) }
            when (serverEntityResult) {
                is Success -> {
                    val serverEntity = serverEntityResult.data
                    val existingByServerId = transactionDao.getByServerId(serverEntity.serverId!!)
                    if (existingByServerId != null) {
                        transactionDao.update(serverEntity)
                    } else {
                        transactionDao.upsertAll(listOf(serverEntity))
                    }
                    val updatedEntity = transactionDao.getByLocalId(generatedLocalId)
                    Success(
                        updatedEntity!!.toTransactionDto(
                            updatedEntity.categoryId,
                            accountId = accountId
                        )
                    )
                }

                is Error -> Error(serverEntityResult.message)
                Loading -> Loading
            }
        } else {
            Success(insertedEntity.toTransactionDto(insertedEntity.categoryId, accountId))
        }
    }

    override suspend fun getLocalChanges(): Resource<List<TransactionEntity>> {
        val localChanges = transactionDao.getLocalChanges()
        return if (localChanges.isNotEmpty()) {
            Success(localChanges)
        } else {
            Error("Нет локальных изменений")
        }
    }

}
