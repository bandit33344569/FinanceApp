package com.abrosimov.account.domain.repository

import com.abrosimov.core.data.models.dto.AccountDto
import com.abrosimov.core.data.models.responses.AccountHistoryResponse
import com.abrosimov.core.data.models.responses.AccountResponse
import com.abrosimov.core.data.models.requests.AccountUpdateRequest
import com.abrosimov.core.domain.models.Account
import com.abrosimov.core.domain.Resource
import retrofit2.Response

interface AccountRepository {
    suspend fun getAccount(): Resource<Account>
    suspend fun getAccountWithStats(
        accountId: Int
    ): Response<AccountResponse>

    suspend fun updateAccount(
        accountId: Int,
        request: AccountUpdateRequest
    ): Response<AccountDto>

    suspend fun getAccountChangesHistory(
        accountId: Int
    ): Response<AccountHistoryResponse>
}