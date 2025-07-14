package com.abrosimov.transactions.history.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abrosimov.impl.repository.AccountDetailsRepository
import com.abrosimov.transactions.history.domain.GetTransactionsHistoryUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class HistoryViewModelFactory @Inject constructor(
    private val getTransactionsHistoryUseCase: GetTransactionsHistoryUseCase,
    private val accountDetailsRepository: AccountDetailsRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        HistoryViewModel(
            getTransactionsHistoryUseCase = getTransactionsHistoryUseCase,
            accountDetailsRepository = accountDetailsRepository
        ) as T
}