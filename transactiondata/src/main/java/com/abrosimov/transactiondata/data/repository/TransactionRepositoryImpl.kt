package com.abrosimov.transactiondata.data.repository

import com.abrosimov.core.data.mappers.toDomain
import com.abrosimov.core.domain.Resource
import com.abrosimov.core.domain.map
import com.abrosimov.core.domain.models.SpecTransaction
import com.abrosimov.network.apiCall.safeApiCall
import com.abrosimov.network.networkMonitor.NetworkMonitor
import com.abrosimov.transactiondata.data.api.TransactionsApi
import com.abrosimov.transactiondata.domain.repository.TransactionRepository
import javax.inject.Inject

/**
 * Реализация репозитория [TransactionRepository], предоставляющая функционал для получения
 * транзакций за определённый период с использованием удалённого API.
 *
 * Использует [NetworkMonitor] для проверки наличия интернет-соединения перед выполнением запросов,
 * и [TransactionsApi] для выполнения сетевых вызовов. Результаты мапятся в доменные модели
 * [SpecTransaction] с помощью метода расширения [toDomain].
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
    ): Resource<List<SpecTransaction>> {
        return safeApiCall(networkMonitor) {
            api.getTransactionsFromPeriod(accountId, startDate, endDate)
        }.map { list ->
            list.map { it.toDomain() }
        }
    }
}