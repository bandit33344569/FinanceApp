package com.abrosimov.impl.repository

import com.abrosimov.api.models.dbo.AccountEntity
import com.abrosimov.api.models.dbo.toDto
import com.abrosimov.api.models.dto.AccountDto
import com.abrosimov.api.models.dto.toAccountRequest
import com.abrosimov.api.models.dto.toEntity
import com.abrosimov.api.models.requests.AccountUpdateRequest
import com.abrosimov.api.models.responses.AccountHistoryResponse
import com.abrosimov.api.models.responses.AccountResponse
import com.abrosimov.api.repository.AccountRepository
import com.abrosimov.api.service.local.AccountDao
import com.abrosimov.api.service.remote.AccountApi
import com.abrosimov.impl.networkMonitor.NetworkMonitor
import com.abrosimov.impl.safeApiCall
import com.abrosimov.utils.dateutils.DateUtils
import com.abrosimov.utils.models.Resource
import com.abrosimov.utils.models.flatMap
import retrofit2.Response
import java.util.Date
import javax.inject.Inject


class AccountRepositoryImpl @Inject constructor(
    private val api: AccountApi,
    private val networkMonitor: NetworkMonitor,
    private val accountDao: AccountDao,
) : AccountRepository {
    override suspend fun getAccount(): Resource<AccountDto> {
        val local = accountDao.getAccount()

        if (isNetworkAvailable()) {
            try {
                val response = api.getAccounts()
                if (response.isSuccessful && response.body().orEmpty().isNotEmpty()) {
                    val remote = response.body()!!.first()
                    val remoteEntity = remote.toEntity()

                    val remoteTimestamp = DateUtils.isoStringToTimestamp(remoteEntity.updatedAt)
                    val localTimestamp = local?.localUpdatedAt?.let { DateUtils.isoStringToTimestamp(it) } ?: 0L

                    val merged = if (local != null) {
                        if (local.localUpdatedAt != null && localTimestamp > remoteTimestamp) {
                            local
                        } else {
                            remoteEntity.copy(localUpdatedAt = remoteEntity.updatedAt)
                        }
                    } else {
                        remoteEntity.copy(localUpdatedAt = remoteEntity.updatedAt)
                    }

                    accountDao.insert(merged)
                    return Resource.Success(merged.toDto())
                }
            } catch (e: Exception) {
            }
        }

        return if (local != null) {
            Resource.Success(local.toDto())
        } else {
            Resource.Error("Аккаунт не найден")
        }
    }


    override suspend fun updateAccount(
        accountId: Int,
        request: AccountUpdateRequest
    ): Resource<AccountDto> {
        val currentAccount = accountDao.getAccount()
            ?: return Resource.Error("Аккаунт не найден")

        val updated = currentAccount.copy(
            name = request.name,
            balance = request.balance,
            currency = request.currency,
            localUpdatedAt = DateUtils.dateToIsoString(Date())
        )

        accountDao.update(updated)

        if (isNetworkAvailable()) {
            syncAccountToServer(updated)
        }

        return Resource.Success(updated.toDto())
    }


    private suspend fun syncAccountToServer(account: AccountEntity) {
        try {
            val dto = account.toDto()
            val response = api.updateAccount(dto.id, dto.toAccountRequest())

            if (response.isSuccessful) {
                val serverUpdatedAt = response.body()?.updatedAt ?: DateUtils.dateToIsoString(Date())
                val synced = account.copy(
                    updatedAt = serverUpdatedAt,
                    localUpdatedAt = serverUpdatedAt
                )
                accountDao.update(synced)
            }
        } catch (e: Exception) {
        }
    }



    private suspend fun isNetworkAvailable(): Boolean {
        return networkMonitor.isOnline()
    }

    private fun getCurrentTimestamp(): String {
        return System.currentTimeMillis().toString()
    }

    override suspend fun getAccountWithStats(accountId: Int): Response<AccountResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountChangesHistory(accountId: Int): Response<AccountHistoryResponse> {
        TODO("Not yet implemented")
    }
}