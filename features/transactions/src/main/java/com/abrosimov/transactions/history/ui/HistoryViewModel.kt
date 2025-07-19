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
 */
class HistoryViewModel @Inject constructor(
    private val getTransactionsHistoryUseCase: GetTransactionsHistoryUseCase,
    private val accountDetailsRepository: AccountDetailsRepository
) : ViewModel() {

    fun getCurrency(): String {
        return accountDetailsRepository.getSelectedCurrency()
    }

    private val _historyTransactions =
        MutableStateFlow<Resource<List<SpecTransaction>>>(Resource.Loading)

    private val _dateRange = MutableStateFlow(
        DateRange(
            DateUtils.getStartOfMonth(DateUtils.today()),
            DateUtils.getEndOfDay(DateUtils.today())
        )

    )

    val dateRange: StateFlow<DateRange> get() = _dateRange

    fun updateDateRange(start: Date, end: Date) {
        _dateRange.value = DateRange(start, end)
    }

    fun updateStartDate(date: Date) {
        _dateRange.update { it.copy(start = date) }
    }

    fun updateEndDate(date: Date) {
        _dateRange.update { it.copy(end = date) }
    }


    fun loadHistoryTransactions() {
        viewModelScope.launch {
            val dateRange = _dateRange.value
            val startDateStr = DateUtils.dateToIsoString(dateRange.start)
            val endDateStr = DateUtils.dateToIsoString(dateRange.end)

            _historyTransactions.value = Resource.Loading
            _historyTransactions.value =
                getTransactionsHistoryUseCase(
                    accountDetailsRepository.getAccountId(),
                    startDateStr,
                    endDateStr
                )
        }
    }

    val historyExpensesSummary = _historyTransactions.map { resource ->
        when (resource) {
            is Resource.Loading -> Resource.Loading
            is Resource.Error -> Resource.Error(resource.message)
            is Resource.Success -> {
                val filteredAndMapped = resource.data
                    .filter { it.category.isIncome == false }
                    .map { it.toExpense() }
                    .sortedByDescending {
                        DateUtils.isoStringToDate(it.data).time
                    }
                val totalAmount = filteredAndMapped.sumOf { it.amount.toDouble() }
                Resource.Success(ExpensesSummary(filteredAndMapped, totalAmount))
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5000),
        Resource.Loading
    )

    val historyIncomesSummary = _historyTransactions.map { resource ->
        when (resource) {
            is Resource.Loading -> Resource.Loading
            is Resource.Error -> Resource.Error(resource.message)
            is Resource.Success -> {
                val filteredAndMapped = resource.data
                    .filter { it.category.isIncome == true }
                    .map { it.toIncome() }
                    .sortedByDescending {
                        DateUtils.isoStringToDate(it.date).time
                    }
                val totalAmount = filteredAndMapped.sumOf { it.amount.toDouble() }
                Resource.Success(IncomesSummary(filteredAndMapped, totalAmount))
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5000),
        Resource.Loading
    )

}