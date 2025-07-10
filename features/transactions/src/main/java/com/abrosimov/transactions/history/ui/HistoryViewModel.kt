package com.abrosimov.transactions.history.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrosimov.impl.models.SpecTransaction
import com.abrosimov.impl.repository.AccountDetailsRepository
import com.abrosimov.transactions.expenses.domain.mappers.toExpense
import com.abrosimov.transactions.expenses.domain.models.ExpensesSummary
import com.abrosimov.transactions.history.domain.GetTransactionsHistoryUseCase
import com.abrosimov.transactions.incomes.domain.mappers.toIncome
import com.abrosimov.transactions.incomes.domain.models.IncomesSummary
import com.abrosimov.ui.models.DateRange
import com.abrosimov.utils.dateutils.DateUtils
import com.abrosimov.utils.models.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

/**
 * ViewModel для экрана "История", отвечающая за загрузку и фильтрацию транзакций по заданному периоду.
 *
 * Содержит:
 * - Состояние списка транзакций ([historyTransactions])
 * - Диапазон дат ([dateRange])
 * - Сводные данные о расходах и доходах ([historyExpensesSummary], [historyIncomesSummary])
 *
 * Использует [com.abrosimov.impl.models.Resource] для обработки состояний:
 * - Успех ([com.abrosimov.impl.models.Resource.Success])
 * - Ошибка ([com.abrosimov.impl.models.Resource.Error])
 * - Загрузка ([com.abrosimov.impl.models.Resource.Loading])
 *
 * @property getTransactionsUseCase Используется для получения транзакций из источника данных.
 */
class HistoryViewModel @Inject constructor(
    private val getTransactionsHistoryUseCase: GetTransactionsHistoryUseCase,
    private val accountDetailsRepository: AccountDetailsRepository
) : ViewModel() {

    fun getCurrency(): String {
        return accountDetailsRepository.getSelectedCurrency()
    }
    /**
     * Текущее состояние списка транзакций, представленное через [kotlinx.coroutines.flow.MutableStateFlow].
     * Наблюдаемое значение используется в UI для отображения прогресса, данных или ошибок.
     */
    private val _historyTransactions =
        MutableStateFlow<Resource<List<SpecTransaction>>>(Resource.Loading)

    /**
     * Диапазон дат, выбранный пользователем для фильтрации транзакций.
     * По умолчанию — с начала текущего месяца до конца сегодняшнего дня.
     */
    private val _dateRange = MutableStateFlow(
        DateRange(
            DateUtils.getStartOfMonth(DateUtils.today()),
            DateUtils.getEndOfDay(DateUtils.today())
        )

    )

    /**
     * Публичный доступ к выбранному диапазону дат.
     * Не позволяет изменять состояние извне (только чтение).
     */
    val dateRange: StateFlow<DateRange> get() = _dateRange

    /**
     * Обновляет диапазон дат на указанный пользователем.
     *
     * @param start Начальная дата периода.
     * @param end Конечная дата периода.
     */
    fun updateDateRange(start: Date, end: Date) {
        _dateRange.value = DateRange(start, end)
    }

    /**
     * Обновляет начальную дату в текущем диапазоне.
     *
     * @param date Новая начальная дата.
     */
    fun updateStartDate(date: Date) {
        _dateRange.update { it.copy(start = date) }
    }

    /**
     * Обновляет конечную дату в текущем диапазоне.
     *
     * @param date Новая конечная дата.
     */
    fun updateEndDate(date: Date) {
        _dateRange.update { it.copy(end = date) }
    }

    /**
     * Асинхронно загружает транзакции за выбранный период.
     *
     * 1. Берет текущий диапазон дат.
     * 2. Преобразует даты в формат сервера.
     * 3. Выполняет запрос через [com.abrosimov.transactiondata.domain.usecase.GetTransactionsUseCase].
     * 4. Обновляет [_historyTransactions] результатом операции.
     */
    fun loadHistoryTransactions() {
        viewModelScope.launch {
            val dateRange = _dateRange.value
            val startDateStr = DateUtils.dateToServerFormat(dateRange.start)
            val endDateStr = DateUtils.dateToServerFormat(dateRange.end)

            _historyTransactions.value = Resource.Loading
            _historyTransactions.value =
                getTransactionsHistoryUseCase(accountDetailsRepository.getAccountId(), startDateStr, endDateStr)
        }
    }

    /**
     * Сводка по расходам за выбранный период.
     *
     * Фильтрует только траты (`isIncome == false`), преобразует их в модель [Expense],
     * считает общую сумму и валюту, возвращает результат как [Resource<ExpensesSummary>].
     */
    val historyExpensesSummary = _historyTransactions.map { resource ->
        when (resource) {
            is Resource.Loading -> Resource.Loading
            is Resource.Error -> Resource.Error(resource.message)
            is Resource.Success -> {
                val filteredAndMapped = resource.data
                    .filter { it.category.isIncome == false }
                    .map { it.toExpense() }
                    .sortedByDescending {
                        DateUtils.isoStringToDate(it.createdAt).time
                    }

                val currency =
                    if (filteredAndMapped.isNotEmpty()) filteredAndMapped[0].currency else "₽"
                val totalAmount = filteredAndMapped.sumOf { it.amount.toDouble() }

                Resource.Success(ExpensesSummary(filteredAndMapped, totalAmount, currency))
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5000),
        Resource.Loading
    )

    /**
     * Сводка по доходам за выбранный период.
     *
     * Фильтрует только доходы (`isIncome == true`), преобразует их в модель [Income],
     * считает общую сумму и валюту, возвращает результат как [Resource<IncomesSummary>].
     */
    val historyIncomesSummary = _historyTransactions.map { resource ->
        when (resource) {
            is Resource.Loading -> Resource.Loading
            is Resource.Error -> Resource.Error(resource.message)
            is Resource.Success -> {
                val filteredAndMapped = resource.data
                    .filter { it.category.isIncome == true }
                    .map { it.toIncome() }
                    .sortedByDescending {
                        DateUtils.isoStringToDate(it.createdAt).time
                    }

                val currency =
                    if (filteredAndMapped.isNotEmpty()) filteredAndMapped[0].currency else "₽"
                val totalAmount = filteredAndMapped.sumOf { it.amount.toDouble() }

                Resource.Success(IncomesSummary(filteredAndMapped, totalAmount, currency))
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5000),
        Resource.Loading
    )

}