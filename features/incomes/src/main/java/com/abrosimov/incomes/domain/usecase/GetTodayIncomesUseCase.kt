package com.abrosimov.incomes.domain.usecase

import com.abrosimov.core.domain.Resource
import com.abrosimov.core.domain.dateUtils.DateUtils
import com.abrosimov.core.domain.map
import com.abrosimov.incomes.domain.mappers.toIncome
import com.abrosimov.incomes.domain.models.IncomesSummary
import com.abrosimov.transactiondata.domain.usecase.GetTransactionsUseCase
import javax.inject.Inject

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
 * @return Результат выполнения операции — [Resource.Success] с данными, [Resource.Error] или [Resource.Loading].
 */
class GetTodayIncomesUseCase @Inject constructor(private val getTransactionsUseCase: GetTransactionsUseCase) {
    suspend operator fun invoke(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    ): Resource<IncomesSummary> {
        val now = DateUtils.today()
        val defaultStartDate = DateUtils.dateToServerFormat(DateUtils.getStartOfDay(now))
        val defaultEndDate = DateUtils.dateToServerFormat(DateUtils.getEndOfDay(now))

        return getTransactionsUseCase.invoke(
            accountId,
            startDate ?: defaultStartDate,
            endDate ?: defaultEndDate
        ).map { transactions ->
            val filteredAndMapped = transactions
                .filter { it.category.isIncome }
                .map { it.toIncome() }

            val currency = filteredAndMapped.firstOrNull()?.currency ?: "₽"
            val totalAmount = filteredAndMapped.sumOf { it.amount.toDouble() }

            IncomesSummary(filteredAndMapped, totalAmount, currency)
        }
    }
}