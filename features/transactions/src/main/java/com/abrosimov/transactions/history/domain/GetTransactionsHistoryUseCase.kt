package com.abrosimov.transactions.history.domain

import com.abrosimov.api.repository.TransactionRepository
import com.abrosimov.impl.models.SpecTransaction
import com.abrosimov.impl.models.mappers.toDomain
import com.abrosimov.utils.dateutils.DateUtils
import com.abrosimov.utils.models.Resource
import com.abrosimov.utils.models.map
import javax.inject.Inject

class GetTransactionsHistoryUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    ): Resource<List<SpecTransaction>> {
        val now = DateUtils.today()
        val defaultStartDate = DateUtils.dateToIsoString(DateUtils.getStartOfMonth(now))
        val defaultEndDate = DateUtils.dateToIsoString(DateUtils.getEndOfDay(now))

        return transactionRepository.getTransactionFromPeriod(
            accountId,
            startDate ?: defaultStartDate,
            endDate ?: defaultEndDate
        ).map { it.map { it.toDomain() } }
    }
}