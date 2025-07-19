package com.abrosimov.api.service.remote

import com.abrosimov.api.models.dto.AccountDto
import com.abrosimov.api.models.requests.AccountUpdateRequest
import com.abrosimov.api.models.responses.AccountHistoryResponse
import com.abrosimov.api.models.responses.AccountResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountApi {
    //получение списка аккаунтов
    @GET("accounts")
    suspend fun getAccounts(): Response<List<AccountDto>>

    //Счет вместе с доходами и расходами
    @GET("accounts/{id}")
    suspend fun getAccountWithStats(
        @Path("id") accountId: Int
    ): Response<AccountResponse>

    //обновление аккаунта по id
    @PUT("accounts/{id}")
    suspend fun updateAccount(
        @Path("id") accountId: Int,
        @Body request: AccountUpdateRequest
    ): Response<AccountDto>

    //история изменений счета
    @GET("accounts/{id}/history")
    suspend fun getAccountChangesHistory(
        @Path("id") accountId: Int
    ): Response<AccountHistoryResponse>
}