package com.abrosimov.financeapp.domain.repo

import com.abrosimov.financeapp.data.network.FinanceApi
import com.abrosimov.financeapp.domain.models.Account
import com.abrosimov.financeapp.domain.models.Category
import com.abrosimov.financeapp.domain.models.SpecTransaction
import com.abrosimov.financeapp.domain.models.Transaction

interface FinanceRepository {
    suspend fun getCategories(): Resource<List<Category>>
    suspend fun getAccount(): Resource<Account>
    suspend fun getTransactionFromPeriod(
        accountId:Int,
        startDate: String? = null,
        endDate: String? = null,
    ): Resource<List<SpecTransaction>>
}