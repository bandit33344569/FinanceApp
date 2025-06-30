package com.abrosimov.transactiondata.domain.usecase

import com.abrosimov.core.domain.Resource
import com.abrosimov.core.domain.models.SpecTransaction
import com.abrosimov.core.domain.retryWithDelay
import com.abrosimov.transactiondata.domain.repository.TransactionRepository
import javax.inject.Inject

/**
 * Use case (случай использования), предназначенный для получения списка транзакций за указанный период.
 *
 * Использует [TransactionRepository] для выполнения сетевого запроса и автоматически обрабатывает возможные ошибки,
 * применяя логику повторных попыток с задержкой, реализованную в функции [retryWithDelay].
 *
 * @property repository Репозиторий, через который выполняется запрос на получение транзакций.
 */
class GetTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    ): Resource<List<SpecTransaction>> {
        return retryWithDelay {
            repository.getTransactionFromPeriod(accountId, startDate, endDate)
        }
    }
}