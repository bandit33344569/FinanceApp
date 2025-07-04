package com.abrosimov.incomes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrosimov.core.data.repository.CurrencyRepository
import com.abrosimov.core.domain.Resource
import com.abrosimov.incomes.domain.models.IncomesSummary
import com.abrosimov.incomes.domain.usecase.GetTodayIncomesUseCase
import com.abrosimov.network.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана "Доходы", отвечающая за загрузку и хранение сводной информации о доходах за сегодня.
 *
 * Содержит состояние в виде [Resource<IncomesSummary>], что позволяет легко обрабатывать:
 * - Успех ([Resource.Success])
 * - Ошибку ([Resource.Error])
 * - Загрузку ([Resource.Loading])
 *
 * @property getTodayIncomesUseCase UseCase, используемый для получения данных о доходах за день.
 */
class IncomesViewModel @Inject constructor(
    private val getTodayIncomesUseCase: GetTodayIncomesUseCase,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    fun getCurrency(): String {
        return currencyRepository.getSelectedCurrency()
    }

    /**
     * Текущее состояние данных о доходах, представленное через [MutableStateFlow].
     * Наблюдаемое значение используется в UI для отображения прогресса, данных или ошибок.
     */
    private val _todayIncomesSummary = MutableStateFlow<Resource<IncomesSummary>>(Resource.Loading)

    /**
     * Публичный доступ к данным о доходах.
     * Не позволяет изменять состояние извне (только чтение).
     */
    val todayIncomesSummary: StateFlow<Resource<IncomesSummary>> = _todayIncomesSummary

    /**
     * Метод для асинхронной загрузки информации о доходах за сегодня.
     *
     * 1. Сначала устанавливает состояние [Resource.Loading].
     * 2. Выполняет запрос через [GetTodayIncomesUseCase].
     * 3. Обновляет [_todayIncomesSummary] результатом операции.
     *
     * @param accountId Идентификатор счета, по которому нужно получить данные (по умолчанию = 10).
     */
    fun loadTodayIncomes() {
        viewModelScope.launch {
            val result = getTodayIncomesUseCase.invoke(accountId = BuildConfig.ACCOUNT_ID)
            _todayIncomesSummary.value = result
        }
    }
}