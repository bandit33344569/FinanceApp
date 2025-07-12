package com.abrosimov.api.repository

import com.abrosimov.api.models.dto.AccountDto
import com.abrosimov.api.models.requests.AccountUpdateRequest
import com.abrosimov.api.models.responses.AccountHistoryResponse
import com.abrosimov.api.models.responses.AccountResponse
import com.abrosimov.utils.models.Resource
import retrofit2.Response

interface AccountRepository {
    suspend fun getAccount(): Resource<AccountDto>
    suspend fun getAccountWithStats(
        accountId: Int
    ): Response<AccountResponse>

    suspend fun updateAccount(
        accountId: Int,
        request: AccountUpdateRequest
    ): Resource<AccountDto>

    suspend fun getAccountChangesHistory(
        accountId: Int
    ): Response<AccountHistoryResponse>
}