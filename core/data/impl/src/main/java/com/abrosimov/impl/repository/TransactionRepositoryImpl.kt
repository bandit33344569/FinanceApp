package com.abrosimov.impl.repository

import com.abrosimov.api.models.dto.TransactionDto
import com.abrosimov.api.models.dto.requests.TransactionRequest
import com.abrosimov.api.models.dto.responses.TransactionResponse
import com.abrosimov.api.repository.TransactionRepository
import com.abrosimov.api.service.remote.TransactionsApi
import com.abrosimov.impl.networkMonitor.NetworkMonitor
import com.abrosimov.impl.safeApiCall
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

    ) : TransactionRepository {
    override suspend fun getTransactionFromPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): Resource<List<TransactionResponse>> {
        return safeApiCall(networkMonitor) {
            api.getTransactionsFromPeriod(accountId, startDate, endDate)
        }
    }

    override suspend fun getTransactionById(transactionId: Int): Resource<TransactionResponse> {
        return safeApiCall(networkMonitor) {
            api.getTransactionFromId(transactionId = transactionId)
        }
    }

    override suspend fun updateTransaction(
        transactionId: Int,
        transactionRequest: TransactionRequest
    ): Resource<TransactionResponse> {
        return safeApiCall(networkMonitor) {
            api.updateTransaction(transactionId, transactionRequest)
        }
    }

    override suspend fun deleteTransaction(transactionId: Int) {
        TODO()
    }

    override suspend fun createTransaction(transactionRequest: TransactionRequest): Resource<TransactionDto> {
        return safeApiCall(networkMonitor) {
            api.createNewTransaction(transactionRequest)
        }
    }
}