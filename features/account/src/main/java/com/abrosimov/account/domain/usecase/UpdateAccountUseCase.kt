package com.abrosimov.account.domain.usecase

import com.abrosimov.api.models.dto.requests.AccountUpdateRequest
import com.abrosimov.api.repository.AccountRepository
import com.abrosimov.impl.models.Account
import com.abrosimov.impl.models.mappers.toDomain
import com.abrosimov.utils.models.Resource
import com.abrosimov.utils.models.map
import com.abrosimov.utils.retryWithDelay
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend fun updateAccount(
        accountId: Int,
        request: AccountUpdateRequest
    ): Resource<Account> {
        return retryWithDelay {
            repository.updateAccount(accountId, request).map { it.toDomain() }
        }
    }
}