package com.abrosimov.transactions.incomes.domain

import com.abrosimov.api.repository.TransactionRepository
import com.abrosimov.impl.models.mappers.toDomain
import com.abrosimov.transactions.incomes.domain.mappers.toIncome
import com.abrosimov.transactions.incomes.domain.models.IncomesSummary
import com.abrosimov.utils.dateutils.DateUtils
import com.abrosimov.utils.models.Resource
import com.abrosimov.utils.models.map
import javax.inject.Inject
import kotlin.collections.map

/**
 * Выполняет запрос на получение информации о доходах за указанный период.
 *
 * 1. Если `startDate` или `endDate` не указаны — используются даты начала и конца текущего дня.
 * 2. Получает список транзакций с сервера.
 * 3. Фильтрует транзакции — оставляет только те, где `isIncome == true`.
 * 4. Преобразует транзакции в модель [Income] через `toIncome()`.
 * 5. Считает общую сумму и определяет валюту.
 * 6. Возвращает результат в виде [Resource<IncomesSummary>].
 *
 * @param accountId Уникальный идентификатор счета.
 * @param startDate Начальная дата периода (в формате сервера). Опционально.
 * @param endDate Конечная дата периода (в формате сервера). Опционально.
 * @return Результат выполнения операции — [com.abrosimov.impl.models.Resource.Success] с данными, [com.abrosimov.impl.models.Resource.Error] или [com.abrosimov.impl.models.Resource.Loading].
 */
class GetTodayIncomesUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    ): Resource<IncomesSummary> {
        val now = DateUtils.today()
        val defaultStartDate = DateUtils.dateToServerFormat(DateUtils.getStartOfDay(now))
        val defaultEndDate = DateUtils.dateToServerFormat(DateUtils.getEndOfDay(now))

        return transactionRepository.getTransactionFromPeriod(
            accountId,
            startDate ?: defaultStartDate,
            endDate ?: defaultEndDate
        ).map { transactions ->
            val filteredAndMapped = transactions.map { it.toDomain() }
                .filter { it.category.isIncome }
                .map { it.toIncome() }

            val currency = filteredAndMapped.firstOrNull()?.currency ?: "₽"
            val totalAmount = filteredAndMapped.sumOf { it.amount.toDouble() }

            IncomesSummary(filteredAndMapped, totalAmount, currency)
        }
    }
}