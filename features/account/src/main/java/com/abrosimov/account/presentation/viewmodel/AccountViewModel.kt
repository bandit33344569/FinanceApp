package com.abrosimov.account.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrosimov.account.domain.usecase.GetAccountUseCase
import com.abrosimov.account.domain.usecase.UpdateAccountUseCase
import com.abrosimov.core.data.models.requests.AccountUpdateRequest
import com.abrosimov.core.data.repository.CurrencyRepository
import com.abrosimov.core.domain.Resource
import com.abrosimov.core.domain.models.Account
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана, отображающего информацию о счете.
 *
 * Содержит состояние счета и предоставляет методы для его загрузки через [com.abrosimov.account.domain.usecase.GetAccountUseCase].
 * Использует [com.abrosimov.core.domain.Resource] для обработки состояний:
 * - Успех ([com.abrosimov.core.domain.Resource.Success])
 * - Ошибка ([com.abrosimov.core.domain.Resource.Error])
 * - Загрузка ([com.abrosimov.core.domain.Resource.Loading])
 *
 * @property getAccountUseCase UseCase, используемый для получения данных о счете.
 */
class AccountViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {
    init {
        Log.d("AccountViewModel", "Создан новый инстанс")
    }

    private val _accountState = MutableStateFlow<Resource<Account>>(Resource.Loading)
    val accountState: StateFlow<Resource<Account>> = _accountState

    private val _editedAccount = MutableStateFlow<Account?>(null)
    val editedAccount: StateFlow<Account?> = _editedAccount

    fun updateAccountName(newName: String) {
        Log.d("AccountViewModel", "Updating name to $newName")
        _editedAccount.value = _editedAccount.value?.copy(name = newName)
    }

    fun updateAccountBalance(newBalance: String) {
        Log.d("AccountViewModel", "Updating balance to $newBalance")
        _editedAccount.value = _editedAccount.value?.copy(balance = newBalance)
        Log.d("AccountViewModel", "значение редактируемого аккаунта ${_editedAccount.value}")
    }

    fun saveAccount() {
        val edited = _editedAccount.value ?: return
        viewModelScope.launch {
            val request = AccountUpdateRequest(
                name = edited.name,
                balance = edited.balance,
                currency = edited.currency
            )
            _accountState.value = updateAccountUseCase.updateAccount(edited.id, request)

            if (_accountState.value is Resource.Success) {
                _editedAccount.value = (_accountState.value as Resource.Success<Account>).data
            }
        }
    }

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
            if (_accountState.value is Resource.Success) {
                _editedAccount.value = (_accountState.value as Resource.Success<Account>).data
                currencyRepository.setSelectedCurrency((_accountState.value as Resource.Success<Account>).data.currency)
            }
        }
    }


    fun updateCurrency(newCurrency: String) {
        val edited = _editedAccount.value ?: return
        val updatedAccount = edited.copy(currency = newCurrency)
        _editedAccount.value = updatedAccount

        viewModelScope.launch {
            val request = AccountUpdateRequest(
                name = updatedAccount.name,
                balance = updatedAccount.balance,
                currency = updatedAccount.currency
            )
            _accountState.value = updateAccountUseCase.updateAccount(updatedAccount.id, request)

            if (_accountState.value is Resource.Success) {
                _editedAccount.value = (_accountState.value as Resource.Success<Account>).data
                currencyRepository.setSelectedCurrency(newCurrency)
            }
        }
    }

}