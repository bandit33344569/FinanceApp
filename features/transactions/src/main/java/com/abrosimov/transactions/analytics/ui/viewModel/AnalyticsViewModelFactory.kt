package com.abrosimov.transactions.analytics.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abrosimov.impl.repository.AccountDetailsRepository
import com.abrosimov.transactions.analytics.domain.GetAnalyticsTransactionsFromPeriodUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class AnalyticsViewModelFactory @Inject constructor(
    private val getAnalyticsTransactionsFromPeriodUseCase: GetAnalyticsTransactionsFromPeriodUseCase,
    private val accountDetailsRepository: AccountDetailsRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        AnalyticsViewModel(
            getAnalyticsUseCase = getAnalyticsTransactionsFromPeriodUseCase,
            accountDetailsRepository = accountDetailsRepository,
        ) as T
}
