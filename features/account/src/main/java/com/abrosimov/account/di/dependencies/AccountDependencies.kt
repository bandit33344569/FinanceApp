package com.abrosimov.account.di.dependencies

import com.abrosimov.api.repository.AccountRepository
import com.abrosimov.impl.repository.AccountDetailsRepository
import com.abrosimov.ui.viewmodel.SharedAppViewModel

interface AccountDependencies {
    val accountRepository: AccountRepository
    val sharedAppViewModel: SharedAppViewModel
    val accountDetailsRepository: AccountDetailsRepository
}