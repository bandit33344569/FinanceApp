package com.abrosimov.transactions.expenses.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abrosimov.impl.repository.AccountDetailsRepository
import com.abrosimov.transactions.expenses.domain.GetTodayExpensesUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class ExpensesViewModelFactory @Inject constructor(
    private val getTodayExpensesUseCase: GetTodayExpensesUseCase,
    private val accountDetailsRepository: AccountDetailsRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        ExpensesViewModel(
            getTodayExpensesUseCase = getTodayExpensesUseCase,
            accountDetailsRepository = accountDetailsRepository
        ) as T
}