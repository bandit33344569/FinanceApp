package com.abrosimov.transactions.analytics.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrosimov.impl.repository.AccountDetailsRepository
import com.abrosimov.transactions.analytics.domain.GetAnalyticsTransactionsFromPeriodUseCase
import com.abrosimov.transactions.analytics.domain.models.ExpenseAnalyticsSummary
import com.abrosimov.transactions.analytics.domain.models.IncomeAnalyticsSummary
import com.abrosimov.ui.models.DateRange
import com.abrosimov.utils.dateutils.DateUtils
import com.abrosimov.utils.models.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

class AnalyticsViewModel @Inject constructor(
    private val getAnalyticsUseCase: GetAnalyticsTransactionsFromPeriodUseCase,
    private val accountDetailsRepository: AccountDetailsRepository
) : ViewModel() {
    private val _incomeState =
        MutableStateFlow<Resource<IncomeAnalyticsSummary>>(Resource.Loading)
    val incomeState: StateFlow<Resource<IncomeAnalyticsSummary>> = _incomeState.asStateFlow()

    private val _expenseState =
        MutableStateFlow<Resource<ExpenseAnalyticsSummary>>(Resource.Loading)
    val expenseState: StateFlow<Resource<ExpenseAnalyticsSummary>> = _expenseState.asStateFlow()

    fun loadAnalytics() {
        viewModelScope.launch {
            val dateRange = _dateRange.value
            val startDateStr = DateUtils.dateToIsoString(dateRange.start)
            val endDateStr = DateUtils.dateToIsoString(dateRange.end)
            loadIncome(startDateStr, endDateStr)
            loadExpense(startDateStr, endDateStr)
        }
    }

    fun getCurrency(): String {
        return accountDetailsRepository.getSelectedCurrency()
    }

    private val _dateRange = MutableStateFlow(
        DateRange(
            DateUtils.getStartOfMonth(DateUtils.today()),
            DateUtils.getEndOfDay(DateUtils.today())
        )

    )
    val dateRange: StateFlow<DateRange> get() = _dateRange

    fun updateStartDate(date: Date) {
        _dateRange.update { it.copy(start = date) }
    }

    fun updateEndDate(date: Date) {
        _dateRange.update { it.copy(end = date) }
    }

    private fun loadIncome(startDate: String?, endDate: String?) = viewModelScope.launch {
        _incomeState.value = getAnalyticsUseCase.getIncomeAnalytics(startDate, endDate)
    }

    private fun loadExpense(startDate: String?, endDate: String?) = viewModelScope.launch {
        _expenseState.value = getAnalyticsUseCase.getExpenseAnalytics(startDate, endDate)
    }
}