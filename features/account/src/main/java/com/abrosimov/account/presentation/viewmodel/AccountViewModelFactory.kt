package com.abrosimov.account.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abrosimov.account.domain.usecase.GetAccountUseCase
import com.abrosimov.account.domain.usecase.GetDataForChartUseCase
import com.abrosimov.account.domain.usecase.UpdateAccountUseCase
import com.abrosimov.impl.repository.AccountDetailsRepository
import javax.inject.Inject

internal class AccountViewModelFactory @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val accountDetailsRepository: AccountDetailsRepository,
    private val getDataForChartUseCase: GetDataForChartUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        AccountViewModel(
            getAccountUseCase = getAccountUseCase,
            updateAccountUseCase = updateAccountUseCase,
            accountDetailsRepository = accountDetailsRepository,
            getDataForChartUseCase = getDataForChartUseCase,

        ) as T
}