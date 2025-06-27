package com.abrosimov.data.repository

import com.abrosimov.account.data.api.AccountApi
import com.abrosimov.core.data.models.dto.AccountDto
import com.abrosimov.core.data.models.responses.AccountHistoryResponse
import com.abrosimov.core.data.models.responses.AccountResponse
import com.abrosimov.core.data.models.requests.AccountUpdateRequest
import com.abrosimov.core.domain.models.Account
import com.abrosimov.account.domain.repository.AccountRepository
import com.abrosimov.core.data.mappers.toDomain
import com.abrosimov.core.domain.Resource
import com.abrosimov.core.domain.flatMap
import com.abrosimov.network.apiCall.safeApiCall
import com.abrosimov.network.networkMonitor.NetworkMonitor
import retrofit2.Response
import javax.inject.Inject

/**
 * Реализация репозитория [AccountRepository], предоставляющая данные о счетах из сети.
 *
 * Использует [AccountApi] для выполнения сетевых запросов и [NetworkMonitor] для проверки подключения.
 * Обрабатывает ответы от сервера с помощью [safeApiCall] и преобразует DTO в доменные модели через `toDomain()`.
 *
 * @property api API-интерфейс для работы с аккаунтами.
 * @property networkMonitor Мониторинг состояния интернет-соединения.
 */
class AccountRepositoryImpl @Inject constructor(
    private val api: AccountApi,
    private val networkMonitor: NetworkMonitor
) : AccountRepository {
    override suspend fun getAccount(): Resource<Account> {
        return safeApiCall(networkMonitor) { api.getAccounts() }.flatMap { list ->
            if (list.isNotEmpty()) {
                Resource.Success(list.first().toDomain())
            } else {
                Resource.Error("Счет не найден")
            }
        }
    }

    override suspend fun getAccountWithStats(accountId: Int): Response<AccountResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun updateAccount(
        accountId: Int,
        request: AccountUpdateRequest
    ): Response<AccountDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountChangesHistory(accountId: Int): Response<AccountHistoryResponse> {
        TODO("Not yet implemented")
    }
}