package com.abrosimov.expenses.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrosimov.core.domain.Resource
import com.abrosimov.expenses.domain.GetTodayExpensesUseCase
import com.abrosimov.expenses.domain.models.ExpensesSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана "Расходы", отвечающая за загрузку и хранение сводной информации о тратах за сегодня.
 *
 * Содержит состояние в виде [Resource<ExpensesSummary>], что позволяет легко обрабатывать:
 * - Успех ([Resource.Success])
 * - Ошибку ([Resource.Error])
 * - Загрузку ([Resource.Loading])
 *
 * @property getTodayExpensesUseCase UseCase, используемый для получения данных о расходах за день.
 */
class ExpensesViewModel @Inject constructor(
    private val getTodayExpensesUseCase: GetTodayExpensesUseCase,
) : ViewModel() {

    /**
     * Текущее состояние данных о расходах, представленное через [MutableStateFlow].
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
     * 1. Сначала устанавливает состояние [Resource.Loading].
     * 2. Выполняет запрос через [GetTodayExpensesUseCase].
     * 3. Обновляет [_todayExpensesSummary] результатом операции.
     *
     * @param accountId Идентификатор счета, по которому нужно получить данные (по умолчанию = 10).
     */
    fun loadTodayExpenses(accountId: Int = 10) {
        viewModelScope.launch {
            val result = getTodayExpensesUseCase.invoke(accountId)
            _todayExpensesSummary.value = result
        }
    }
}