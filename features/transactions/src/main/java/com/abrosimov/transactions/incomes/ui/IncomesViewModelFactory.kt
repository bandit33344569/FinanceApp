package com.abrosimov.transactions.incomes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abrosimov.impl.repository.AccountDetailsRepository
import com.abrosimov.transactions.incomes.domain.GetTodayIncomesUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class IncomesViewModelFactory @Inject constructor(
    private val getTodayIncomesUseCase: GetTodayIncomesUseCase,
    private val accountDetailsRepository: AccountDetailsRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        IncomesViewModel(
            getTodayIncomesUseCase = getTodayIncomesUseCase,
            accountDetailsRepository = accountDetailsRepository
        ) as T
}