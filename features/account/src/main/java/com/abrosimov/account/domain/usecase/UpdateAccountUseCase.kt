package com.abrosimov.account.domain.usecase

import com.abrosimov.account.domain.repository.AccountRepository
import com.abrosimov.core.data.models.requests.AccountUpdateRequest
import com.abrosimov.core.domain.Resource
import com.abrosimov.core.domain.models.Account
import com.abrosimov.core.domain.retryWithDelay
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend fun updateAccount(
        accountId: Int,
        request: AccountUpdateRequest
    ): Resource<Account> {
        return retryWithDelay {
            repository.updateAccount(accountId, request)
        }
    }
}