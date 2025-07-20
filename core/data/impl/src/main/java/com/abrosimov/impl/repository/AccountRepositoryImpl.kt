package com.abrosimov.impl.repository

import com.abrosimov.api.models.dto.AccountDto
import com.abrosimov.api.models.requests.AccountUpdateRequest
import com.abrosimov.api.models.responses.AccountHistoryResponse
import com.abrosimov.api.models.responses.AccountResponse
import com.abrosimov.api.repository.AccountRepository
import com.abrosimov.api.service.remote.AccountApi
import com.abrosimov.impl.networkMonitor.NetworkMonitor
import com.abrosimov.impl.safeApiCall
import com.abrosimov.utils.models.Resource
import com.abrosimov.utils.models.flatMap
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
    override suspend fun getAccount(): Resource<AccountDto> {
        return safeApiCall(networkMonitor) { api.getAccounts() }.flatMap { list ->
            if (list.isNotEmpty()) {
                Resource.Success(list.first())
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
    ): Resource<AccountDto> {
        return safeApiCall(networkMonitor) {
            api.updateAccount(accountId, request)
        }
    }

    override suspend fun getAccountChangesHistory(accountId: Int): Response<AccountHistoryResponse> {
        TODO("Not yet implemented")
    }
}