package com.abrosimov.expenses.domain

import com.abrosimov.core.domain.Resource
import com.abrosimov.core.domain.dateUtils.DateUtils
import com.abrosimov.core.domain.map
import com.abrosimov.expenses.domain.mappers.toExpense
import com.abrosimov.expenses.domain.models.ExpensesSummary
import com.abrosimov.transactiondata.domain.usecase.GetTransactionsUseCase
import javax.inject.Inject

/**
 * UseCase для получения сводки по расходам за сегодня.
 *
 * Получает транзакции через [GetTransactionsUseCase], фильтрует их (оставляя только расходы),
 * вычисляет общую сумму и возвращает результат в виде [ExpensesSummary].
 *
 * Если даты не переданы, используется текущий день (с 00:00 до 23:59).
 *
 * @property getTransactionsUseCase Используется для получения списка транзакций из источника данных.
 */
class GetTodayExpensesUseCase @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase
) {
    suspend operator fun invoke(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    ): Resource<ExpensesSummary> {
        val now = DateUtils.today()
        val defaultStartDate = DateUtils.dateToServerFormat(DateUtils.getStartOfDay(now))
        val defaultEndDate = DateUtils.dateToServerFormat(DateUtils.getEndOfDay(now))

        return getTransactionsUseCase.invoke(
            accountId,
            startDate ?: defaultStartDate,
            endDate ?: defaultEndDate
        ).map { transactions ->
            val filteredAndMapped = transactions
                .filter { !it.category.isIncome }
                .map { it.toExpense() }

            val currency = filteredAndMapped.firstOrNull()?.currency ?: "₽"
            val totalAmount = filteredAndMapped.sumOf { it.amount.toDouble() }

            ExpensesSummary(filteredAndMapped, totalAmount, currency)
        }
    }

}