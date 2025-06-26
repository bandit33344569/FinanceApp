package com.abrosimov.financeapp.data.repo

import com.abrosimov.financeapp.data.mapper.toDomain
import com.abrosimov.financeapp.data.network.FinanceApi
import com.abrosimov.financeapp.data.network.NetworkMonitor
import com.abrosimov.financeapp.data.network.safeApiCall
import com.abrosimov.financeapp.domain.models.Account
import com.abrosimov.financeapp.domain.models.Category
import com.abrosimov.financeapp.domain.repo.FinanceRepository
import com.abrosimov.financeapp.domain.repo.Resource
import com.abrosimov.financeapp.domain.repo.flatMap
import com.abrosimov.financeapp.domain.repo.map
import com.abrosimov.financeapp.domain.models.SpecTransaction
import jakarta.inject.Inject

class FinanceRepositoryImpl @Inject constructor(
    private val api: FinanceApi,
    private val networkMonitor: NetworkMonitor
) : FinanceRepository {

    override suspend fun getCategories(): Resource<List<Category>> {
        return safeApiCall(networkMonitor) { api.getAllCategories() }
            .map { list -> list.map { it.toDomain() } }
    }

    //Получаем один(единственный) счет с бэка
    override suspend fun getAccount(): Resource<Account> {
        return safeApiCall(networkMonitor) { api.getAccounts() }.flatMap { list ->
            if (list.isNotEmpty()) {
                Resource.Success(list.first().toDomain())
            } else {
                Resource.Error("Счет не найден")
            }
        }
    }
    //TransactionResponse -> SpecTransaction
    override suspend fun getTransactionFromPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): Resource<List<SpecTransaction>> {
        return safeApiCall(networkMonitor) {
            api.getTransactionsFromPeriod(accountId, startDate, endDate)
        }.map { list ->
            list.map { it.toDomain() }
        }
    }
}