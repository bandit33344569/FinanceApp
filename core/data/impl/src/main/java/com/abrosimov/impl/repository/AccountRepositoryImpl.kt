package com.abrosimov.impl.repository

import com.abrosimov.api.models.dto.AccountDto
import com.abrosimov.api.models.dto.requests.AccountUpdateRequest
import com.abrosimov.api.models.dto.responses.AccountHistoryResponse
import com.abrosimov.api.models.dto.responses.AccountResponse
import com.abrosimov.api.repository.AccountRepository
import com.abrosimov.api.service.local.AccountDao
import com.abrosimov.api.service.remote.AccountApi
import com.abrosimov.impl.models.mappers.toDto
import com.abrosimov.impl.models.mappers.toEntity
import com.abrosimov.impl.networkMonitor.NetworkMonitor
import com.abrosimov.impl.safeApiCall
import com.abrosimov.utils.dateutils.DateUtils
import com.abrosimov.utils.models.Resource
import retrofit2.Response
import javax.inject.Inject

/**
 * Реализация репозитория [AccountRepository], предоставляющая данные о счетах.
 *
 * @property accountDao DAO-интерфейс для работы с аккаунтами
 * @property api API-интерфейс для работы с аккаунтами.
 * @property networkMonitor Мониторинг состояния интернет-соединения.
 */
class AccountRepositoryImpl @Inject constructor(
    private val api: AccountApi,
    private val networkMonitor: NetworkMonitor,
    private val accountDao: AccountDao,
) : AccountRepository {
    override suspend fun getAccount(): Resource<AccountDto> {
        return try {
            val account = accountDao.getAccount()
            if (account != null) {
                Resource.Success(account.toDto())
            } else {
                Resource.Error("Account not found in database")
            }
        } catch (e: Exception) {
            Resource.Error("Failed to fetch account from database: ${e.message}")
        }
    }

    override suspend fun getAccountWithStats(accountId: Int): Response<AccountResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun updateAccount(
        accountId: Int,
        request: AccountUpdateRequest
    ): Resource<AccountDto> {
        val currentAccount = accountDao.getAccount()
        return if (currentAccount == null) {
            Resource.Error("No account found to update")
        } else {
            val updatedAccount = request.toEntity(
                currentAccountEntity = currentAccount
            )
            accountDao.update(updatedAccount)
            Resource.Success(updatedAccount.toDto())
        }
    }

    override suspend fun getAccountChangesHistory(accountId: Int): Response<AccountHistoryResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun syncAccount() {
        if (!networkMonitor.isOnline()) return
        val localAccount = accountDao.getAccount()
        val result = safeApiCall(networkMonitor) { api.getAccounts() }
        if (result !is Resource.Success || result.data.isEmpty()) {
            if (result is Resource.Error) result.message else "No account found"
            return
        }
        val serverAccount = result.data.first()
        if (localAccount == null) {
            val syncedAccount =
                serverAccount.toEntity(localUpdatedAt = serverAccount.updatedAt)
            accountDao.insert(syncedAccount)
        } else {
            val serverUpdatedAt = DateUtils.isoStringToLong(serverAccount.updatedAt)
            val localUpdatedAt = localAccount.localUpdatedAt ?: 0L
            if (serverUpdatedAt > localUpdatedAt) {
                val syncedAccount =
                    serverAccount.toEntity(localUpdatedAt = serverAccount.updatedAt)
                accountDao.update(syncedAccount)
            } else if (serverUpdatedAt < localUpdatedAt) {
                val localRequest = AccountUpdateRequest(
                    name = localAccount.name,
                    balance = localAccount.balance,
                    currency = localAccount.currency
                )
                when (val result = safeApiCall(networkMonitor){api.updateAccount(localAccount.id, localRequest)}){
                    is Resource.Error -> return
                    Resource.Loading -> return
                    is Resource.Success -> {
                        val syncedAccount = result.data.toEntity(localUpdatedAt = result.data.updatedAt)
                        accountDao.update(syncedAccount)
                    }
                }
            }
        }
    }
}