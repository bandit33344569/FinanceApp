package com.abrosimov.account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrosimov.account.domain.usecase.GetAccountUseCase
import com.abrosimov.core.domain.Resource
import com.abrosimov.core.domain.models.Account
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана, отображающего информацию о счете.
 *
 * Содержит состояние счета и предоставляет методы для его загрузки через [GetAccountUseCase].
 * Использует [Resource] для обработки состояний:
 * - Успех ([Resource.Success])
 * - Ошибка ([Resource.Error])
 * - Загрузка ([Resource.Loading])
 *
 * @property getAccountUseCase UseCase, используемый для получения данных о счете.
 */
class AccountViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
) : ViewModel() {
    /**
     * Текущее состояние счета, представленное через [MutableStateFlow].
     * Наблюдаемое значение используется в UI для отображения прогресса, данных или ошибок.
     */
    private val _accountState = MutableStateFlow<Resource<Account>>(Resource.Loading)

    /**
     * Публичный доступ к состоянию счета.
     * Не позволяет изменять состояние извне (только чтение).
     */
    val accountState: StateFlow<Resource<Account>> = _accountState

    /**
     * Метод для асинхронной загрузки информации о счете.
     *
     * 1. Сначала устанавливает состояние [Resource.Loading].
     * 2. Выполняет запрос через [GetAccountUseCase].
     * 3. Обновляет [_accountState] результатом операции.
     */
    fun loadAccount() {
        viewModelScope.launch {
            _accountState.value = Resource.Loading
            _accountState.value = getAccountUseCase()
        }
    }
}