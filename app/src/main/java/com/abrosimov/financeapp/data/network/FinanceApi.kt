package com.abrosimov.financeapp.data.network

import com.abrosimov.financeapp.data.models.AccountDto
import com.abrosimov.financeapp.data.models.CategoryDto
import com.abrosimov.financeapp.data.models.Response.AccountHistoryResponse
import com.abrosimov.financeapp.data.models.Response.AccountResponse
import com.abrosimov.financeapp.data.models.Request.AccountUpdateRequest
import com.abrosimov.financeapp.data.models.Request.TransactionRequest
import com.abrosimov.financeapp.data.models.Response.TransactionResponse
import com.abrosimov.financeapp.data.models.TransactionDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface FinanceApi {
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

    //список всех категорий: расходов и доходов
    @GET("categories")
    suspend fun getAllCategories(): Response<List<CategoryDto>>

    //список категроий по типу
    @GET("categories/type/{isIncome}")
    suspend fun getCategoriesByType(): Response<List<CategoryDto>>

    //создание новой транзации
    @POST("transactions")
    suspend fun createNewTransaction(
        @Body request: TransactionRequest
    ): Response<TransactionDto>

    //транзакция по id
    @GET("transactions/{id}")
    suspend fun getTransactionFromId(
        @Path("id") transactionId: Int
    ): Response<TransactionDto>

    //обновление транзакции по id
    @PUT("transactions/{id}")
    suspend fun updateTransaction(
        @Path("id") transactionId: Int,
        @Body request: TransactionRequest
    ): Response<TransactionDto>

    //удаление транзакции
    @DELETE("transactions/{id}")
    suspend fun deleteTransaction(
        @Path("id") transactionId: Int,
    )
    //получение транзакций за указанный период
    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionsFromPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null
    ): Response<List<TransactionResponse>>
}