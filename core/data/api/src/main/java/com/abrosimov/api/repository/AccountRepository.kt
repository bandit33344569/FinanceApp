package com.abrosimov.api.repository

import com.abrosimov.api.models.dto.AccountDto
import com.abrosimov.api.models.dto.requests.AccountUpdateRequest
import com.abrosimov.api.models.dto.responses.AccountHistoryResponse
import com.abrosimov.api.models.dto.responses.AccountResponse
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

    suspend fun syncAccount()
}