package com.abrosimov.account.domain.usecase

import com.abrosimov.api.repository.AccountRepository
import com.abrosimov.impl.models.Account
import com.abrosimov.impl.models.mappers.toDomain
import com.abrosimov.utils.models.Resource
import com.abrosimov.utils.models.map
import com.abrosimov.utils.retryWithDelay
import javax.inject.Inject

/**
 * UseCase для получения информации о счете с поддержкой повторных попыток.
 *
 * Использует [com.abrosimov.api.repository.AccountRepository] для получения данных и применяет логику повтора с задержкой
 * в случае сетевых или временных ошибок.
 *
 * @property repository Репозиторий, предоставляющий данные о счете.
 */
class GetAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(): Resource<Account> {
        return retryWithDelay {
            repository.getAccount().map { it.toDomain() }
        }
    }
}