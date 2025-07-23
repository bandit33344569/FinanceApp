package com.abrosimov.transactions.edit_transaction.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrosimov.api.models.dto.requests.TransactionRequest
import com.abrosimov.impl.models.Category
import com.abrosimov.impl.repository.AccountDetailsRepository
import com.abrosimov.transactions.edit_transaction.domain.CreateTransactionUseCase
import com.abrosimov.transactions.edit_transaction.domain.GetCategoriesUseCase
import com.abrosimov.transactions.edit_transaction.domain.GetTransactionByIdUseCase
import com.abrosimov.transactions.edit_transaction.domain.UpdateTransactionUseCase
import com.abrosimov.transactions.edit_transaction.ui.state.TransactionFormState
import com.abrosimov.transactions.edit_transaction.ui.state.TransactionLoadState
import com.abrosimov.utils.dateutils.DateUtils
import com.abrosimov.utils.models.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

class TransactionEditViewModel @Inject constructor(
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val accountDetailsRepository: AccountDetailsRepository,
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    val transactionState = MutableStateFlow<TransactionLoadState>(TransactionLoadState.Loading)

    private val _incomesCategories = MutableStateFlow<Resource<List<Category>>>(Resource.Loading)
    val incomesCategories = _incomesCategories

    private val _expensesCategories = MutableStateFlow<Resource<List<Category>>>(Resource.Loading)
    val expensesCategories = _expensesCategories

    fun loadCategories() {
        viewModelScope.launch {
            _incomesCategories.value = getCategoriesUseCase.getIncomeCategories()
            _expensesCategories.value = getCategoriesUseCase.getExpenseCategories()
        }
    }

    fun loadTransaction(id: Int?) {
        if (id == null) {
            transactionState.value = TransactionLoadState.Success(TransactionFormState())
            return
        }

        viewModelScope.launch {
            when (val result = getTransactionByIdUseCase(id)) {
                is Resource.Success -> {
                    val transaction = result.data
                    val formState = TransactionFormState(
                        id = transaction.id,
                        amount = transaction.amount.toString(),
                        comment = transaction.comment,
                        date = DateUtils.getDateStringFromIso(transaction.transactionDate),
                        time = DateUtils.getTimeStringFromIso(transaction.transactionDate),
                        category = transaction.category
                    )
                    transactionState.value = TransactionLoadState.Success(formState)
                }
                is Resource.Error -> {
                    transactionState.value = TransactionLoadState.Error(result.message)
                }
                is Resource.Loading -> {
                    transactionState.value = TransactionLoadState.Loading
                }
            }
        }
    }

    fun setComment(comment: String?) {
        val currentState = transactionState.value
        if (currentState is TransactionLoadState.Success) {
            transactionState.update {
                currentState.copy(
                    state = currentState.state.copy(comment = comment)
                )
            }
        }
    }

    fun setCategory(category: Category) {
        val currentState = transactionState.value
        if (currentState is TransactionLoadState.Success) {
            transactionState.update {
                currentState.copy(
                    state = currentState.state.copy(category = category)
                )
            }
        }
    }

    fun setAmount(amount: String) {
        val currentState = transactionState.value
        if (currentState is TransactionLoadState.Success) {
            transactionState.update {
                currentState.copy(
                    state = currentState.state.copy(amount = amount)
                )
            }
        }
    }

    fun setDate(date: Date) {
        val currentState = transactionState.value
        if (currentState is TransactionLoadState.Success) {
            val isoDateString = DateUtils.getDateStringFromIso(DateUtils.dateToIsoString(date))
            transactionState.update {
                currentState.copy(
                    state = currentState.state.copy(date = isoDateString)
                )
            }
        }
    }

    fun setTime(time: String) {
        val currentState = transactionState.value
        if (currentState is TransactionLoadState.Success) {
            transactionState.update {
                currentState.copy(
                    state = currentState.state.copy(time = time)
                )
            }
        }
    }

    fun saveTransaction() {
        val currentState = transactionState.value
        if (currentState !is TransactionLoadState.Success) return

        val state = currentState.state
        val request = TransactionRequest(
            accountId = accountDetailsRepository.accountId.value,
            amount = (state.amount.toDoubleOrNull() ?: 0.0).toString(),
            categoryId = state.category?.id ?: 0,
            comment = state.comment,
            transactionDate = DateUtils.combineDateAndTimeToIso(state.date, state.time)
        )

        viewModelScope.launch {
            if (state.id == null) {
                createTransactionUseCase.invoke(request)
            } else {
                updateTransactionUseCase.invoke(state.id, request)
            }
        }
    }
}
