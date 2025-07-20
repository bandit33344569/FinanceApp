package com.abrosimov.api.service.remote

import com.abrosimov.api.models.dto.TransactionDto
import com.abrosimov.api.models.dto.requests.TransactionRequest
import com.abrosimov.api.models.dto.responses.TransactionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionsApi {
    // получение транзакий за период
    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionsFromPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null
    ): Response<List<TransactionResponse>>
    //создание транзакции
    @POST("transactions")
    suspend fun createNewTransaction(
        @Body request: TransactionRequest
    ): Response<TransactionDto>

    //транзакция по id
    @GET("transactions/{id}")
    suspend fun getTransactionFromId(
        @Path("id") transactionId: Int
    ): Response<TransactionResponse>

    //обновление транзакции по id
    @PUT("transactions/{id}")
    suspend fun updateTransaction(
        @Path("id") transactionId: Int,
        @Body request: TransactionRequest
    ): Response<TransactionResponse>

    //удаление транзакции
    @DELETE("transactions/{id}")
    suspend fun deleteTransaction(
        @Path("id") transactionId: Int,
    )
}