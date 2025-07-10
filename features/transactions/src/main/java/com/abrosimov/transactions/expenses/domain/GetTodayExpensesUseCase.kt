package com.abrosimov.transactions.expenses.domain

import com.abrosimov.api.repository.TransactionRepository
import com.abrosimov.impl.models.mappers.toDomain
import com.abrosimov.transactions.expenses.domain.mappers.toExpense
import com.abrosimov.transactions.expenses.domain.models.ExpensesSummary
import com.abrosimov.utils.dateutils.DateUtils
import com.abrosimov.utils.models.Resource
import com.abrosimov.utils.models.map
import javax.inject.Inject

/**
 * UseCase для получения сводки по расходам за сегодня.
 *
 * Получает транзакции через [com.abrosimov.transactiondata.domain.usecase.GetTransactionsUseCase], фильтрует их (оставляя только расходы),
 * вычисляет общую сумму и возвращает результат в виде [com.abrosimov.expenses.domain.models.ExpensesSummary].
 *
 * Если даты не переданы, используется текущий день (с 00:00 до 23:59).
 *
 * @property getTransactionsUseCase Используется для получения списка транзакций из источника данных.
 */
class GetTodayExpensesUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    ): Resource<ExpensesSummary> {
        val now = DateUtils.today()
        val defaultStartDate = DateUtils.dateToServerFormat(DateUtils.getStartOfDay(now))
        val defaultEndDate = DateUtils.dateToServerFormat(DateUtils.getEndOfDay(now))

        return transactionRepository.getTransactionFromPeriod(
            accountId,
            startDate ?: defaultStartDate,
            endDate ?: defaultEndDate
        ).map { transactions ->
            val filteredAndMapped = transactions.map { it.toDomain() }
                .filter { !it.category.isIncome }
                .map { it.toExpense() }
            val currency = filteredAndMapped.firstOrNull()?.currency ?: "₽"
            val totalAmount = filteredAndMapped.sumOf { it.amount.toDouble() }

            ExpensesSummary(filteredAndMapped, totalAmount, currency)
        }
    }

}