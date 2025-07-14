package com.abrosimov.transactions.expenses.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrosimov.impl.repository.AccountDetailsRepository
import com.abrosimov.transactions.expenses.domain.GetTodayExpensesUseCase
import com.abrosimov.transactions.expenses.domain.models.ExpensesSummary
import com.abrosimov.utils.models.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана "Расходы", отвечающая за загрузку и хранение сводной информации о тратах за сегодня.
 *
 * Содержит состояние в виде [Resource<ExpensesSummary>], что позволяет легко обрабатывать:
 * - Успех ([com.abrosimov.impl.models.Resource.Success])
 * - Ошибку ([com.abrosimov.impl.models.Resource.Error])
 * - Загрузку ([com.abrosimov.impl.models.Resource.Loading])
 *
 * @property getTodayExpensesUseCase UseCase, используемый для получения данных о расходах за день.
 */
class ExpensesViewModel @Inject constructor(
    private val getTodayExpensesUseCase: GetTodayExpensesUseCase,
    private val accountDetailsRepository: AccountDetailsRepository
) : ViewModel() {

    /**
     * Текущее состояние данных о расходах, представленное через [kotlinx.coroutines.flow.MutableStateFlow].
     * Наблюдаемое значение используется в UI для отображения прогресса, данных или ошибок.
     */
    private val _todayExpensesSummary =
        MutableStateFlow<Resource<ExpensesSummary>>(Resource.Loading)

    /**
     * Публичный доступ к данным о расходах.
     * Не позволяет изменять состояние извне (только чтение).
     */
    val todayExpensesSummary: StateFlow<Resource<ExpensesSummary>> = _todayExpensesSummary

    /**
     * Метод для асинхронной загрузки информации о расходах за сегодня.
     *
     * 1. Сначала устанавливает состояние [com.abrosimov.impl.models.Resource.Loading].
     * 2. Выполняет запрос через [com.abrosimov.expenses.domain.GetTodayExpensesUseCase].
     * 3. Обновляет [_todayExpensesSummary] результатом операции.
     *
     * @param accountId Идентификатор счета, по которому нужно получить данные (по умолчанию = 10).
     */
    fun getCurrency(): String {
        return accountDetailsRepository.getSelectedCurrency()
    }
    fun loadTodayExpenses() {
        viewModelScope.launch {
            val result = getTodayExpensesUseCase.invoke(accountId = accountDetailsRepository.getAccountId())
            _todayExpensesSummary.value = result
        }
    }
}