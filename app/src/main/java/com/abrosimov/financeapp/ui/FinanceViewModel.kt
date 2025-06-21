package com.abrosimov.financeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrosimov.financeapp.domain.DateUtils.DateUtils
import com.abrosimov.financeapp.domain.models.Account
import com.abrosimov.financeapp.domain.models.Category
import com.abrosimov.financeapp.domain.models.SpecTransaction
import com.abrosimov.financeapp.domain.repo.Resource
import com.abrosimov.financeapp.domain.usecase.GetAccountUseCase
import com.abrosimov.financeapp.domain.usecase.GetCategoriesUseCase
import com.abrosimov.financeapp.domain.usecase.GetTransactionsUseCase
import com.abrosimov.financeapp.ui.models.ExpensesSummary
import com.abrosimov.financeapp.ui.models.IncomesSummary
import com.abrosimov.financeapp.ui.models.mappers.toExpense
import com.abrosimov.financeapp.ui.models.mappers.toIncome
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.abrosimov.financeapp.ui.models.DateRange
import kotlinx.coroutines.flow.update
import java.util.Date

/**
 * ViewModel планирую потом разделить на модели для каждого экрана, пока что так
 */
@HiltViewModel
class FinanceViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {
    private val _accountState = MutableStateFlow<Resource<Account>>(Resource.Loading)
    val accountState: StateFlow<Resource<Account>> = _accountState


    private val _categories = MutableStateFlow<Resource<List<Category>>>(Resource.Loading)
    val categories: StateFlow<Resource<List<Category>>> = _categories

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    val filteredCategoriesWithResource: StateFlow<Resource<List<Category>>> = combine(
        categories,
        searchQuery
    ) { resource, query ->
        when (resource) {
            is Resource.Loading -> Resource.Loading
            is Resource.Error -> Resource.Error(resource.message)
            is Resource.Success -> {
                val filteredList = if (query.isBlank()) {
                    resource.data
                } else {
                    resource.data.filter {
                        it.name.contains(query, ignoreCase = true)
                    }
                }

                if (filteredList.isEmpty() && query.isNotBlank()) {
                    Resource.Success(emptyList())
                } else {
                    Resource.Success(filteredList)
                }
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        Resource.Loading
    )


    private val _todayTransactions =
        MutableStateFlow<Resource<List<SpecTransaction>>>(Resource.Loading)

    private val _historyTransactions =
        MutableStateFlow<Resource<List<SpecTransaction>>>(Resource.Loading)


    fun loadAccount() {
        viewModelScope.launch {
            _accountState.value = Resource.Loading
            _accountState.value = getAccountUseCase()
        }
    }


    fun loadCategories() {
        viewModelScope.launch {
            _categories.value = Resource.Loading
            _categories.value = getCategoriesUseCase()
        }
    }

    fun loadHistoryTransactions() {
        viewModelScope.launch {
            val dateRange = _dateRange.value
            val startDateStr = DateUtils.dateToServerFormat(dateRange.start)
            val endDateStr = DateUtils.dateToServerFormat(dateRange.end)

            _historyTransactions.value = Resource.Loading
            _historyTransactions.value =
                getTransactionsUseCase(accountId = 10, startDateStr, endDateStr)
        }
    }

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
        SharingStarted.WhileSubscribed(5000),
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
        SharingStarted.WhileSubscribed(5000),
        Resource.Loading
    )

    fun loadTodayTransactions() {
        viewModelScope.launch {
            val now = DateUtils.today()
            val startDate = DateUtils.dateToServerFormat(DateUtils.getStartOfDay(now))
            val endDate = DateUtils.dateToServerFormat(DateUtils.getEndOfDay(now))
            _todayTransactions.value = getTransactionsUseCase(10, startDate, endDate)
        }
    }


    val todayExpensesSummary = _todayTransactions.map { resource ->
        when (resource) {
            is Resource.Loading -> Resource.Loading
            is Resource.Error -> Resource.Error(resource.message)
            is Resource.Success -> {
                val filteredAndMapped = resource.data
                    .filter { it.category.isIncome == false }
                    .map { it.toExpense() }
                val currency =
                    if (filteredAndMapped.isNotEmpty()) filteredAndMapped[0].currency else "₽"
                val totalAmount = filteredAndMapped.sumOf { it.amount.toDouble() }

                Resource.Success(ExpensesSummary(filteredAndMapped, totalAmount, currency))
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        Resource.Loading
    )

    val todayIncomesSummary = _todayTransactions.map { resource ->
        when (resource) {
            is Resource.Loading -> Resource.Loading
            is Resource.Error -> Resource.Error(resource.message)
            is Resource.Success -> {
                val filteredAndMapped = resource.data
                    .filter { it.category.isIncome == true }
                    .map { it.toIncome() }
                val currency =
                    if (filteredAndMapped.isNotEmpty()) filteredAndMapped[0].currency else "₽"
                val totalAmount = filteredAndMapped.sumOf { it.amount.toDouble() }

                Resource.Success(IncomesSummary(filteredAndMapped, totalAmount, currency))
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        Resource.Loading
    )

}